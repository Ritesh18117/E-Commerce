package com.ecommerce.backend.entities;

import jakarta.persistence.*;

import java.util.Date;

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

    public Seller() {
    }

    public Seller(Long id, User user, String contactInfo, String companyName, String companyType, String gstNumber,
                  String licenceNumber, String contactEmail, String website, String address, String approvalStatus,
                  Date statusChangeDate, Admin verifiedBy) {
        this.id = id;
        this.user = user;
        this.contactInfo = contactInfo;
        this.companyName = companyName;
        this.companyType = companyType;
        this.gstNumber = gstNumber;
        this.licenceNumber = licenceNumber;
        this.contactEmail = contactEmail;
        this.website = website;
        this.address = address;
        this.approvalStatus = approvalStatus;
        this.statusChangeDate = statusChangeDate;
        this.verifiedBy = verifiedBy;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long sellerId) {
        this.id = sellerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public Admin getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(Admin verifiedBy) {
        this.verifiedBy = verifiedBy;
    }
}
