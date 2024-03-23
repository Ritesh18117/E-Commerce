package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    String findNameByUserId(@Param("user_Id") Long userId);

    Admin findByUserId(Long userId);
}
