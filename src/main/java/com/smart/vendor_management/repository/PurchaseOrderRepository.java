package com.smart.vendor_management.repository;

import com.smart.vendor_management.entity.PurchaseOrder;
import com.smart.vendor_management.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findByVendor(Vendor vendor);

    List<PurchaseOrder> findByStatus(String status);
}