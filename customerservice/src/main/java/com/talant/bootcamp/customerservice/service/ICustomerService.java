package com.talant.bootcamp.customerservice.service;

import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;

import java.util.UUID;

public interface ICustomerService {

    public boolean existsById(UUID id );

    public CustomerDTO createCustomer(CustomerDTO customerToCreate);

    public CustomerEntity getCustomer(UUID id);

    public CustomerEntity updateCustomer(CustomerDTO customer);

}
