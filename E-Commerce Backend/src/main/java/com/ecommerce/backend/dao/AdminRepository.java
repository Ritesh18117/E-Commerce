package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUserId(Long userId);
}
