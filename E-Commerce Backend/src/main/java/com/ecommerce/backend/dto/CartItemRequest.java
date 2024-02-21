package com.ecommerce.backend.dto;

public class CartItemRequest {
    private Long product_id;
    private String size;
    private int quantity;
    public CartItemRequest() {
    }

    public CartItemRequest(Long product_id,  String size, int quantity) {
        this.product_id = product_id;
        this.size = size;
        this.quantity = quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
