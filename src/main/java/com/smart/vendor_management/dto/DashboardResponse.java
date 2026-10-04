package com.smart.vendor_management.dto;

public class DashboardResponse {

    private long totalVendors;
    private long totalProducts;
    private long lowStockProducts;
    private long pendingOrders;
    private long totalSales;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalVendors,
                             long totalProducts,
                             long lowStockProducts,
                             long pendingOrders,
                             long totalSales) {
        this.totalVendors = totalVendors;
        this.totalProducts = totalProducts;
        this.lowStockProducts = lowStockProducts;
        this.pendingOrders = pendingOrders;
        this.totalSales = totalSales;
    }

    public long getTotalVendors() {
        return totalVendors;
    }

    public void setTotalVendors(long totalVendors) {
        this.totalVendors = totalVendors;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(long lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }

    public long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(long totalSales) {
        this.totalSales = totalSales;
    }
}