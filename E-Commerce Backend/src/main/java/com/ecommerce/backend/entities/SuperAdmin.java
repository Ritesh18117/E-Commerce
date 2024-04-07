package com.ecommerce.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "super_admin")
public class SuperAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "super_admin_id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String name;
    private String PersonalEmail;
    private String contact;
    private String gender;
    private String address;
    public SuperAdmin() {
    }

    public SuperAdmin(Long id, User user, String name, String personalEmail, String contact, String gender, String address) {
        this.id = id;
        this.user = user;
        this.name = name;
        PersonalEmail = personalEmail;
        this.contact = contact;
        this.gender = gender;
        this.address = address;
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

    public String getPersonalEmail() {
        return PersonalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        PersonalEmail = personalEmail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
