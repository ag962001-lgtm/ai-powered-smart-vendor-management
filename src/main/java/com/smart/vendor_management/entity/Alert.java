package com.smart.vendor_management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate alertDate;

    private String status;

    public Alert() {
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(LocalDate alertDate) {
        this.alertDate = alertDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}