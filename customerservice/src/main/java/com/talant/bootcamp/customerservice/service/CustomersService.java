package com.talant.bootcamp.customerservice.service;

import com.talant.bootcamp.customerservice.exception.BadRequestException;
import com.talant.bootcamp.customerservice.mapper.CustomerMapper;
import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import com.talant.bootcamp.customerservice.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public CustomerDTO getCustomer(UUID id) {
        CustomerEntity customer = customersRepository.findById(id)
                .orElseThrow(()->new BadRequestException("Customer not found with ID: "+id));
        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customer) {

        CustomerEntity existing = customersRepository.findById(customer.id())
                .orElseThrow(() -> new BadRequestException("Customer not found"));

        if (!existing.getEmail().equals(customer.email()) && customersRepository.existsByEmail(customer.email())) {
            throw new BadRequestException("Customer email already in use");
        }

        existing.setName(customer.name());
        existing.setBirthday(customer.birthday());
        existing.setEmail(customer.email());

        CustomerEntity updated = customersRepository.save(existing);
        return customerMapper.toDtoToShow(updated);
    }


    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customersRepository.findAll().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());

    }

}
