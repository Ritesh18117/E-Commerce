package com.ecommerce.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long id;
    private String name;
    private String contact;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String address1;
    private String address2;
    @NotNull(message = "City cannot be null")
    @NotBlank(message = "City must not be Blank!")
    private String city;
    @NotNull(message = "State cannot be null")
    @NotBlank(message = "State must not be Blank!")
    private String state;
    @NotNull(message = "Country cannot be null")
    @NotBlank(message = "Country must not be Blank!")
    private String country;
    @NotNull(message = "Zipcode cannot be null")
    @NotBlank(message = "Zipcode must not be Blank!")
    private String zipcode;

}
