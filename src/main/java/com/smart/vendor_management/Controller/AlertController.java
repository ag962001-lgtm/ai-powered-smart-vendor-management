package com.smart.vendor_management.Controller;

import com.smart.vendor_management.entity.Alert;
import com.smart.vendor_management.service.AlertService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    // Create low-stock alert
    @PostMapping("/{productId}")
    public ResponseEntity<?> createLowStockAlert(
            @PathVariable Long productId) {

        try {
            return ResponseEntity.ok(
                    alertService.createLowStockAlert(productId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(409)
                    .body(e.getMessage());
        }
    }

    // Get all alerts
    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    // Resolve alert
    @PutMapping("/{id}/resolve")
    public Alert resolveAlert(
            @PathVariable Long id) {

        return alertService.resolveAlert(id);
    }
}