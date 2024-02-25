package com.ecommerce.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "product_variation")
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_variation_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String size;
    private int quantity;
    public ProductVariation() {
    }

    public ProductVariation(Long id, Product product, String size, int quantity) {
        this.id = id;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ProductVariation{" +
                "id=" + id +
                ", product=" + product +
                ", size=" + size +
                ", quantity=" + quantity +
                '}';
    }

}
