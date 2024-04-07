package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Admin;
import com.ecommerce.backend.entities.SuperAdmin;
import org.springframework.data.repository.CrudRepository;

public interface SuperAdminRepository extends CrudRepository<SuperAdmin,Long> {
    SuperAdmin findByUserId(Long userId);
}
