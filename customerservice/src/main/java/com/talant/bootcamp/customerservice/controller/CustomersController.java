package com.talant.bootcamp.customerservice.controller;

import com.talant.bootcamp.customerservice.mapper.CustomerMapper;
import com.talant.bootcamp.customerservice.mapper.CustomerMapperImpl;
import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/customers")
@RestController
public class CustomersController {

    @Autowired
    CustomerMapper customerMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAllCustomers(){
        return new ArrayList<>();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(){
        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setId(UUID.randomUUID());
        newCustomer.setEmail("provisional@example.com");
        newCustomer.setBirthday(LocalDate.now().minusYears(19));
        newCustomer.setName("Provisional Name");
        //Devolución provisional para evitar el fallo:
        return customerMapper.toDtoToShow(newCustomer);
    }

}
