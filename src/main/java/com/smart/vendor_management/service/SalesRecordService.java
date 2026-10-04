package com.smart.vendor_management.service;

import com.smart.vendor_management.entity.Product;
import com.smart.vendor_management.entity.SalesRecord;
import com.smart.vendor_management.repository.ProductRepository;
import com.smart.vendor_management.repository.SalesRecordRepository;
import org.springframework.stereotype.Service;
import com.smart.vendor_management.entity.Alert;
import com.smart.vendor_management.repository.AlertRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesRecordService {

    private final SalesRecordRepository salesRecordRepository;
    private final ProductRepository productRepository;
    private final AlertRepository alertRepository;

    public SalesRecordService(
            SalesRecordRepository salesRecordRepository,
            ProductRepository productRepository,
            AlertRepository alertRepository) {

        this.salesRecordRepository = salesRecordRepository;
        this.productRepository = productRepository;
        this.alertRepository = alertRepository;
    }

    // RECORD A SALE
    public SalesRecord recordSale(Long productId, int quantitySold) {

        // Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Validate quantity
        if (quantitySold <= 0) {
            throw new RuntimeException("Quantity sold must be greater than 0");
        }

        // Check stock
        if (product.getStock() < quantitySold) {
            throw new RuntimeException("Insufficient stock");
        }

        // Decrease stock
        product.setStock(product.getStock() - quantitySold);

        Product savedProduct = productRepository.save(product);

        // Create sales record
        SalesRecord salesRecord = new SalesRecord();

        salesRecord.setProduct(savedProduct);
        salesRecord.setQuantitySold(quantitySold);
        salesRecord.setSaleDate(LocalDate.now());

        SalesRecord savedSale = salesRecordRepository.save(salesRecord);

        // Check for low stock
        if (savedProduct.getStock() <= savedProduct.getThreshold()) {

            // Prevent duplicate NEW alerts
            boolean alertExists =
                    alertRepository.existsByProductAndStatus(
                            savedProduct,
                            "NEW"
                    );

            if (!alertExists) {

                Alert alert = new Alert();

                alert.setProduct(savedProduct);

                alert.setMessage(
                        "Low stock alert: "
                                + savedProduct.getProductName()
                                + " has only "
                                + savedProduct.getStock()
                                + " units left. Threshold is "
                                + savedProduct.getThreshold()
                );

                alert.setAlertDate(LocalDate.now());
                alert.setStatus("NEW");

                alertRepository.save(alert);
            }
        }

        return savedSale;
    }
    // GET ALL SALES RECORDS
    public List<SalesRecord> getAllSalesRecords() {
        return salesRecordRepository.findAll();
    }
}