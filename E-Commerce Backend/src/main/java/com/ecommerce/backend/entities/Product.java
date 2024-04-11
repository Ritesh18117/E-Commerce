package com.ecommerce.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "image_data", length = 1000000)
    private String image;
    private String approvalStatus;
    @Column(name = "imageList", length = 1000000) // Specify the column length here
    private List<String> images;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verified_by_admin_id")
    private Admin verifiedBy;
    private Date AddedDate;
}
