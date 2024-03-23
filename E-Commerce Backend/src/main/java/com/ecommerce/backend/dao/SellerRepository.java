package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SellerRepository extends CrudRepository<Seller, Long> {
    Seller findByUserId(Long userId);
    List<Seller> findAllByApprovalStatus(String approvalStatus);
    Seller findByGstNumber(String gstNumber);
    Seller findByLicenceNumber(String licenceNumber);
    Seller findByCompanyName(String companyName);
}
