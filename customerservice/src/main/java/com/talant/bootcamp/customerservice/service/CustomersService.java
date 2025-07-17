package com.talant.bootcamp.customerservice.service;

import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomersService implements ICustomerService {

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public CustomerEntity createCustomer(CustomerDTO customerToCreate) {
        //1. Validar que no exista con
        return null;
    }

    @Override
    public CustomerEntity getCustomer(UUID id) {
        return null;
    }

    @Override
    public CustomerEntity updateCustomer(CustomerDTO customer) {
        return null;
    }
}
