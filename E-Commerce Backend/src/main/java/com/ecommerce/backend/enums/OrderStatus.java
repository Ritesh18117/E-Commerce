package com.ecommerce.backend.enums;

public enum OrderStatus {
    ORDER_PLACED("Order Placed"),
    PACKED("Packed"),
    SHIPPED("Shipped"),
    AT_DELIVERY_CENTRE("At Delivery Centre"),
    OUT_FOR_DELIVERY("Out for Delivery"),
    DELIVERED("Delivered");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}

