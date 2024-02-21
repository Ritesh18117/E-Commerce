package com.ecommerce.backend.dto;

import com.ecommerce.backend.entities.Address;

import java.util.List;

public class OrderRequest {
    private List<ProductRequest> products;
    private Address address;
    private String comment;

    public OrderRequest(List<ProductRequest> products, Address address, String comment) {
        this.products = products;
        this.address = address;
        this.comment = comment;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
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

    @Override
    public String toString() {
        return "OrderRequest{" +
                "products=" + products +
                ", address=" + address +
                ", comment='" + comment + '\'' +
                '}';
    }
}