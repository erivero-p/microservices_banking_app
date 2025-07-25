package com.talant.bootcamp.customerservice.controller;

import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.service.CustomersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/customers")
@RestController
public class CustomersController {

    @Autowired
    CustomersService customersService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAllCustomers(){
        return customersService.getAllCustomers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerDTO dto){
        return this.customersService.createCustomer(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable UUID id, @Valid @RequestBody CustomerDTO dto) {
        CustomerDTO dtoToUpdate = new CustomerDTO(
                id,
                dto.name(),
                dto.birthday(),
                dto.email()
        );
        return customersService.updateCustomer(dtoToUpdate);
    }

}
