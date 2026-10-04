package com.smart.vendor_management.repository;

import com.smart.vendor_management.entity.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRecordRepository extends JpaRepository<SalesRecord, Long> {
}