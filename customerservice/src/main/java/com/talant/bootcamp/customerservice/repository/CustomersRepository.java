package com.talant.bootcamp.customerservice.repository;

import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<CustomerEntity, UUID> {

    boolean existsByEmail(String email);


}

