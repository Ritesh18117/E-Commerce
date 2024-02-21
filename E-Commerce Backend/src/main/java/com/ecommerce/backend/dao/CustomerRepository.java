package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByUserId(Long userId);
}
