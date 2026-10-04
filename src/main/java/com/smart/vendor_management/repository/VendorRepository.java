package com.smart.vendor_management.repository;

import com.smart.vendor_management.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}