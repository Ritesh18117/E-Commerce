package com.ecommerce.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "admin_id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String name;
    private String contact;
    private String personalMailId;
    private String address;
    private String status;

    private List<Long> verifiedSellers;

    private List<Long> verifiedProducts;


    public void addSeller(Long sellerId) {
        verifiedSellers.add(sellerId);
    }

    public void removeSeller(Long sellerId) {
        verifiedSellers.remove(sellerId);
    }

    public void addProduct(Long productId) {
        verifiedProducts.add(productId);
    }

    public void removeProduct(Long productId) {
        verifiedProducts.remove(productId);
    }
}
