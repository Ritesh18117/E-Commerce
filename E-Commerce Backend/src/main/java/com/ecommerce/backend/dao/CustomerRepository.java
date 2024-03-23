package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByUserId(Long userId);
    String findNameByUserId(@Param("user_id") Long userId);
}
