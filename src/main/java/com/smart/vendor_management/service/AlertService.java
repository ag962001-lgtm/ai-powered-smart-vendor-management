package com.smart.vendor_management.service;

import com.smart.vendor_management.entity.Alert;
import com.smart.vendor_management.entity.Product;
import com.smart.vendor_management.repository.AlertRepository;
import com.smart.vendor_management.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final ProductRepository productRepository;

    public AlertService(
            AlertRepository alertRepository,
            ProductRepository productRepository) {

        this.alertRepository = alertRepository;
        this.productRepository = productRepository;
    }

    // Create alert for a low-stock product
    public Alert createLowStockAlert(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check whether the product is actually low in stock
        if (product.getStock() > product.getThreshold()) {
            throw new RuntimeException(
                    "Product stock is sufficient. No alert required."
            );
        }

        // Prevent duplicate NEW alerts
        boolean alertExists =
                alertRepository.existsByProductAndStatus(
                        product,
                        "NEW"
                );

        if (alertExists) {
            throw new RuntimeException(
                    "A NEW alert already exists for this product."
            );
        }

        Alert alert = new Alert();

        alert.setProduct(product);

        alert.setMessage(
                "Low stock alert: " +
                        product.getProductName() +
                        " has only " +
                        product.getStock() +
                        " units left. Threshold is " +
                        product.getThreshold()
        );

        alert.setAlertDate(LocalDate.now());
        alert.setStatus("NEW");

        return alertRepository.save(alert);
    }

    // Get all alerts
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    // Resolve an alert
    public Alert resolveAlert(Long alertId) {

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        if (alert.getStatus().equals("RESOLVED")) {
            throw new RuntimeException("Alert is already resolved");
        }

        alert.setStatus("RESOLVED");

        return alertRepository.save(alert);
    }
}