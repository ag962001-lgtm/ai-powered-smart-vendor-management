package com.smart.vendor_management.service;

import com.smart.vendor_management.dto.DemandForecastResponse;
import com.smart.vendor_management.entity.Product;
import com.smart.vendor_management.entity.SalesRecord;
import com.smart.vendor_management.repository.ProductRepository;
import com.smart.vendor_management.repository.SalesRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DemandForecastService {

    private final ProductRepository productRepository;
    private final SalesRecordRepository salesRecordRepository;
    private final AIService aiService;

    public DemandForecastService(
            ProductRepository productRepository,
            SalesRecordRepository salesRecordRepository,
            AIService aiService) {

        this.productRepository = productRepository;
        this.salesRecordRepository = salesRecordRepository;
        this.aiService = aiService;
    }

    public DemandForecastResponse forecastDemand(Long productId) {

        // Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Get all sales records
        List<SalesRecord> salesRecords = salesRecordRepository.findAll();

        // Keep only sales records for this product
        List<SalesRecord> productSales = salesRecords.stream()
                .filter(sale -> sale.getProduct().getId().equals(productId))
                .toList();

        // No sales history
        if (productSales.isEmpty()) {

            return new DemandForecastResponse(
                    product.getId(),
                    product.getProductName(),
                    product.getStock(),
                    0,
                    0,
                    "Not enough sales history to forecast demand."
            );
        }

        // Find first and last sale dates
        LocalDate firstDate = productSales.stream()
                .map(SalesRecord::getSaleDate)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());

        LocalDate lastDate = productSales.stream()
                .map(SalesRecord::getSaleDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());

        long days = ChronoUnit.DAYS.between(firstDate, lastDate) + 1;

        if (days <= 0) {
            days = 1;
        }

        // Calculate total quantity sold
        int totalSold = productSales.stream()
                .mapToInt(SalesRecord::getQuantitySold)
                .sum();

        // Average daily demand
        double averageDailyDemand = (double) totalSold / days;

        // Predict demand for next 7 days
        int predictedDemand =
                (int) Math.ceil(averageDailyDemand * 7);

        // Calculate recommended reorder quantity
        int recommendedReorderQuantity =
                Math.max(0, predictedDemand - product.getStock());

        // Ask AI for recommendation
        String aiRecommendation = aiService.getRecommendation(
                product.getProductName(),
                product.getStock(),
                predictedDemand
        );

        return new DemandForecastResponse(
                product.getId(),
                product.getProductName(),
                product.getStock(),
                predictedDemand,
                recommendedReorderQuantity,
                aiRecommendation
        );
    }
}