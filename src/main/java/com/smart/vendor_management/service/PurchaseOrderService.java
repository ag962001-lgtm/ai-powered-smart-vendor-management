package com.smart.vendor_management.service;

import com.smart.vendor_management.dto.DemandForecastResponse;
import com.smart.vendor_management.dto.PurchaseOrderRequest;
import com.smart.vendor_management.entity.Product;
import com.smart.vendor_management.entity.PurchaseOrder;
import com.smart.vendor_management.entity.Vendor;
import com.smart.vendor_management.repository.ProductRepository;
import com.smart.vendor_management.repository.PurchaseOrderRepository;
import com.smart.vendor_management.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;
    private final DemandForecastService demandForecastService;

    public PurchaseOrderService(
            PurchaseOrderRepository purchaseOrderRepository,
            VendorRepository vendorRepository,
            ProductRepository productRepository,
            DemandForecastService demandForecastService) {

        this.purchaseOrderRepository = purchaseOrderRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
        this.demandForecastService = demandForecastService;
    }

    // CREATE PURCHASE ORDER
    public PurchaseOrder createPurchaseOrder(PurchaseOrderRequest request) {

        Vendor vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setVendor(vendor);
        purchaseOrder.setProduct(product);
        purchaseOrder.setQuantity(request.getQuantity());

        purchaseOrder.setStatus("PENDING");
        purchaseOrder.setOrderDate(LocalDate.now());

        return purchaseOrderRepository.save(purchaseOrder);
    }

    // GET ALL PURCHASE ORDERS
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }
    // GET PURCHASE ORDERS BY VENDOR
    public List<PurchaseOrder> getPurchaseOrdersByVendor(Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return purchaseOrderRepository.findByVendor(vendor);
    }

    // UPDATE PURCHASE ORDER STATUS
    public PurchaseOrder updateStatus(Long id, String newStatus) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));

        String currentStatus = purchaseOrder.getStatus();

        // Prevent stock from being added twice
        if (currentStatus.equals("DELIVERED")) {
            throw new RuntimeException("Purchase Order is already delivered");
        }

        // Validate status
        if (!newStatus.equals("PENDING")
                && !newStatus.equals("CONFIRMED")
                && !newStatus.equals("SHIPPED")
                && !newStatus.equals("DELIVERED")) {

            throw new RuntimeException("Invalid status");
        }

        purchaseOrder.setStatus(newStatus);

        // If order is delivered, increase product stock
        if (newStatus.equals("DELIVERED")) {

            Product product = purchaseOrder.getProduct();

            int currentStock = product.getStock();
            int orderQuantity = purchaseOrder.getQuantity();

            product.setStock(currentStock + orderQuantity);

            productRepository.save(product);
        }

        return purchaseOrderRepository.save(purchaseOrder);
    }

    // AI/FORECAST-BASED AUTOMATIC REORDER
    public PurchaseOrder createAutomaticReorder(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check whether the product has a vendor
        if (product.getVendor() == null) {
            throw new RuntimeException("No vendor assigned to this product");
        }

        // Get demand forecast
        DemandForecastResponse forecast =
                demandForecastService.forecastDemand(productId);

        int reorderQuantity =
                forecast.getRecommendedReorderQuantity();

        // Check whether reorder is actually required
        if (reorderQuantity <= 0) {
            throw new RuntimeException(
                    "Forecast indicates that no reorder is required."
            );
        }

        Vendor vendor = product.getVendor();

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setVendor(vendor);
        purchaseOrder.setProduct(product);
        purchaseOrder.setQuantity(reorderQuantity);
        purchaseOrder.setStatus("PENDING");
        purchaseOrder.setOrderDate(LocalDate.now());

        return purchaseOrderRepository.save(purchaseOrder);
    }
}