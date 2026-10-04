package com.smart.vendor_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private int stock;

    private int threshold;
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    public Product() {
    }

    public Product(String productName, int stock, int threshold) {
        this.productName = productName;
        this.stock = stock;
        this.threshold = threshold;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}