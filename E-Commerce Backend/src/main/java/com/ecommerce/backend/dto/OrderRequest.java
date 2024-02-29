package com.ecommerce.backend.dto;

import com.ecommerce.backend.entities.Address;

import java.util.List;

public class OrderRequest {
    private List<ProductRequest> productVariations;
    private Address address;
    private String comment;

    public OrderRequest(List<ProductRequest> productVariations, Address address, String comment) {
        this.productVariations = productVariations;
        this.address = address;
        this.comment = comment;
    }

    public List<ProductRequest> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(List<ProductRequest> productVariations) {
        this.productVariations = productVariations;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}