package com.ecommerce.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seller_id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String contactInfo;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "company_type")
    private String companyType;
    @Column(name = "gst_number")
    private String gstNumber;
    @Column(name = "licence_number")
    private String licenceNumber;
    @Column(name = "contact_email")
    private String contactEmail;
    private String website;
    private String address;
    private String approvalStatus;
    private Date statusChangeDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verified_by_admin_id")
    private Admin verifiedBy;
    private String comment;

    @Column(name = "gst_document", length = 1000000) // Adjust length as needed
    private String gstDocument; // Base64 encoded GST document

    @Column(name = "licence_document", length = 1000000) // Adjust length as needed
    private String licenceDocument; // Base64 encoded licence document

}
