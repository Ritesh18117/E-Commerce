package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Optional<String> findRoleByUsername(String username);
}