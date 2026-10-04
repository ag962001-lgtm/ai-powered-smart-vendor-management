package com.smart.vendor_management.repository;

import com.smart.vendor_management.entity.Alert;
import com.smart.vendor_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    boolean existsByProductAndStatus(Product product, String status);

    List<Alert> findByProductAndStatus(Product product, String status);
}