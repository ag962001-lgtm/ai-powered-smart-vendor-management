package com.smart.vendor_management.Controller;

import com.smart.vendor_management.dto.PurchaseOrderRequest;
import com.smart.vendor_management.dto.PurchaseOrderStatusRequest;
import com.smart.vendor_management.entity.PurchaseOrder;
import com.smart.vendor_management.service.PurchaseOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    // CREATE PURCHASE ORDER
    @PostMapping
    public PurchaseOrder createPurchaseOrder(
            @RequestBody PurchaseOrderRequest request) {

        return purchaseOrderService.createPurchaseOrder(request);
    }

    // GET ALL PURCHASE ORDERS
    @GetMapping
    public List<PurchaseOrder> getAllPurchaseOrders() {

        return purchaseOrderService.getAllPurchaseOrders();
    }
    // GET PURCHASE ORDERS BY VENDOR
    @GetMapping("/vendor/{vendorId}")
    public List<PurchaseOrder> getPurchaseOrdersByVendor(
            @PathVariable Long vendorId) {

        return purchaseOrderService.getPurchaseOrdersByVendor(vendorId);
    }

    // UPDATE PURCHASE ORDER STATUS
    @PutMapping("/{id}/status")
    public PurchaseOrder updateStatus(
            @PathVariable Long id,
            @RequestBody PurchaseOrderStatusRequest request) {

        return purchaseOrderService.updateStatus(
                id,
                request.getStatus()
        );
    }
    @PostMapping("/auto-reorder/{productId}")
    public PurchaseOrder createAutomaticReorder(
            @PathVariable Long productId) {

        return purchaseOrderService.createAutomaticReorder(productId);
    }

}