package com.smart.vendor_management.service;

import com.smart.vendor_management.dto.DashboardResponse;
import com.smart.vendor_management.repository.ProductRepository;
import com.smart.vendor_management.repository.PurchaseOrderRepository;
import com.smart.vendor_management.repository.SalesRecordRepository;
import com.smart.vendor_management.repository.VendorRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SalesRecordRepository salesRecordRepository;

    public DashboardService(VendorRepository vendorRepository,
                            ProductRepository productRepository,
                            PurchaseOrderRepository purchaseOrderRepository,
                            SalesRecordRepository salesRecordRepository) {

        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.salesRecordRepository = salesRecordRepository;
    }

    public DashboardResponse getDashboard() {

        long totalVendors = vendorRepository.count();

        long totalProducts = productRepository.count();

        long lowStockProducts =
                productRepository.findLowStockProducts().size();

        long pendingOrders =
                purchaseOrderRepository.findByStatus("PENDING").size();

        long totalSales =
                salesRecordRepository.findAll()
                        .stream()
                        .mapToLong(sale -> sale.getQuantitySold())
                        .sum();

        return new DashboardResponse(
                totalVendors,
                totalProducts,
                lowStockProducts,
                pendingOrders,
                totalSales
        );
    }
}