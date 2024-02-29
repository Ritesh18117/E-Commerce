package com.ecommerce.backend.dto;

public class ProductRequest {
    //productVariationId
    private Long id;
    private int quantity;

    public ProductRequest(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
