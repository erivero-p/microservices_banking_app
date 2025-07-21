package com.talant.bootcamp.customerservice.service;

import com.talant.bootcamp.customerservice.exception.BadRequestException;
import com.talant.bootcamp.customerservice.mapper.CustomerMapper;
import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import com.talant.bootcamp.customerservice.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomersService implements ICustomerService {

    @Autowired
    CustomersRepository customersRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerToCreate) {

        if(customersRepository.existsByEmail(customerToCreate.email())) {
            throw new BadRequestException("Customer email already in use");
        }
        CustomerEntity customerSaved = customersRepository.save(customerMapper.toEntityToCreate(customerToCreate));

        return customerMapper.toDtoToShow(customerSaved);
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
