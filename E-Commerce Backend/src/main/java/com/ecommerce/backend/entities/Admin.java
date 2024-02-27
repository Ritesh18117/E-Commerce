package com.ecommerce.backend.entities;

import jakarta.persistence.*;

import java.util.List;

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



    public Admin() {
    }

    public Admin(Long id, User user, String name, String contact, String personalMailId, String address, String status, List<Long> verifiedSeller, List<Long> verifiedProduct) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.contact = contact;
        this.personalMailId = personalMailId;
        this.address = address;
        this.status = status;
        this.verifiedSellers = verifiedSeller;
        this.verifiedProducts = verifiedProduct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPersonalMailId() {
        return personalMailId;
    }

    public void setPersonalMailId(String personalMailId) {
        this.personalMailId = personalMailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Long> getVerifiedSeller() {
        return verifiedSellers;
    }

    public void setVerifiedSeller(List<Long> verifiedSeller) {
        this.verifiedSellers = verifiedSeller;
    }

    public List<Long> getVerifiedProduct() {
        return verifiedProducts;
    }

    public void setVerifiedProduct(List<Long> verifiedProduct) {
        this.verifiedProducts = verifiedProduct;
    }

    public void addSeller(Long sellerId) {
        System.out.println("AddSeller");
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
