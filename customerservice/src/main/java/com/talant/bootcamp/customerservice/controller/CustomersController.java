package com.talant.bootcamp.customerservice.controller;

import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/customers")
@RestController
public class CustomersController {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAllCustomers(){
        return new ArrayList<>();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(){

        //Devolución provisional para evitar el fallo:
        return new CustomerDTO(UUID.randomUUID(), "Provisional customer for testing");
    }

}
