package com.ecommerce.backend.dto;

import com.ecommerce.backend.entities.Product;

import java.util.ArrayList;

public class ProductVariation {
    private Product product;
    private ArrayList<ArrayList<String>> size_quant;

    public ProductVariation() {
    }

    public ProductVariation(Product product, ArrayList<ArrayList<String>> size_quant) {
        this.product = product;
        this.size_quant = size_quant;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<ArrayList<String>> getSize_quant() {
        return size_quant;
    }

    public void setSize_quant(ArrayList<ArrayList<String>> size_quant) {
        this.size_quant = size_quant;
    }
}
