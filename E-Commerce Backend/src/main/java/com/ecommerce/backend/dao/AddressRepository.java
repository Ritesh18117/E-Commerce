package com.ecommerce.backend.dao;

import com.ecommerce.backend.entities.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address,Long> {
    List<Address> getAddressesByCustomerId(Long Customer_id);
}
