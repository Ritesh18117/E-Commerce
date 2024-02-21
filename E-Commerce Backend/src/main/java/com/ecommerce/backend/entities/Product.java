package com.ecommerce.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @NotNull(message = "Product Name cannot be null")
    @NotBlank(message = "Product Name must not be Blank!")
    private String name;
    @NotNull(message = "Price cannot be null")
    private Double price;
    private Double discount;
    @NotNull(message = "Margin cannot be null")
    private Double margin;
    private String gender;
    private String color;
    private String description;
    private String imageURL;
    private String approvalStatus;

    public Product() {
    }

    public Product(Long id, Seller seller, Category category, String color, String name, Double price, Double discount, Double margin, String gender, String description, String imageURL, String approvalStatus) {
        this.id = id;
        this.seller = seller;
        this.category = category;
        this.name = name;
        this.color = color;
        this.price = price;
        this.discount = discount;
        this.margin = margin;
        this.gender = gender;
        this.description = description;
        this.imageURL = imageURL;
        this.approvalStatus = approvalStatus;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
