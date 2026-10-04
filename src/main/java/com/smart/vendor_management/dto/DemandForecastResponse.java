package com.smart.vendor_management.dto;

public class DemandForecastResponse {

    private Long productId;
    private String productName;
    private int currentStock;
    private int predictedDemand;
    private int recommendedReorderQuantity;
    private String message;

    public DemandForecastResponse() {
    }

    public DemandForecastResponse(
            Long productId,
            String productName,
            int currentStock,
            int predictedDemand,
            int recommendedReorderQuantity,
            String message) {

        this.productId = productId;
        this.productName = productName;
        this.currentStock = currentStock;
        this.predictedDemand = predictedDemand;
        this.recommendedReorderQuantity = recommendedReorderQuantity;
        this.message = message;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getPredictedDemand() {
        return predictedDemand;
    }

    public void setPredictedDemand(int predictedDemand) {
        this.predictedDemand = predictedDemand;
    }

    public int getRecommendedReorderQuantity() {
        return recommendedReorderQuantity;
    }

    public void setRecommendedReorderQuantity(int recommendedReorderQuantity) {
        this.recommendedReorderQuantity = recommendedReorderQuantity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}