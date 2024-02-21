package com.ecommerce.backend.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long id;
//    @NotNull(message = "Email cannot be null")
//    @NotBlank(message = "Email cannot be blank")
//    @Email(message = "Invalid email format")
//    private String email;
//    @NotNull(message = "Password cannot be null")
//    @NotBlank(message = "Password must not be blank")
//    @Size(min = 8, message = "Size must be atleast of 8 Character")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "Password Constraint Violation")
//    private String password;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String contact;
    private String name;
    private String gender;
    @Column(name = "dob")
    private Date dateOfBirth;

    public Customer() {
    }

    public Customer(Long id, User user, String contact, String name, String gender, Date dateOfBirth) {
        this.id = id;
        this.user = user;
        this.contact = contact;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
