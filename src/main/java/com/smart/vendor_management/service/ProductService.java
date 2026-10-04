package com.smart.vendor_management.service;

import com.smart.vendor_management.entity.Alert;
import com.smart.vendor_management.entity.Product;
import com.smart.vendor_management.repository.AlertRepository;
import com.smart.vendor_management.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AlertRepository alertRepository;

    public ProductService(
            ProductRepository productRepository,
            AlertRepository alertRepository) {

        this.productRepository = productRepository;
        this.alertRepository = alertRepository;
    }

    // ADD PRODUCT
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // GET ALL PRODUCTS
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // DELETE PRODUCT
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // UPDATE PRODUCT
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setStock(product.getStock());
        existingProduct.setThreshold(product.getThreshold());

        return productRepository.save(existingProduct);
    }

    // GET LOW-STOCK PRODUCTS
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    // INCREASE STOCK
    public Product increaseStock(Long id, int quantity) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStock(product.getStock() + quantity);

        return productRepository.save(product);
    }

    // DECREASE STOCK
    // Automatically creates an alert if stock reaches threshold
    public Product decreaseStock(Long id, int quantity) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check for insufficient stock
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        // Decrease stock
        product.setStock(product.getStock() - quantity);

        // Save updated product
        Product savedProduct = productRepository.save(product);

        // Automatically create low-stock alert
        if (savedProduct.getStock() <= savedProduct.getThreshold()) {

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

        return savedProduct;
    }
}