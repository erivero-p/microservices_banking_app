package com.talant.bootcamp.customerservice.controller;

import com.talant.bootcamp.customerservice.mapper.CustomerMapper;
import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import com.talant.bootcamp.customerservice.service.CustomersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.UUID;

@WebMvcTest(CustomersController.class)
@DisplayName("Book Controller Tests with WebMvcTest")
public class CustomersControllerTest {

    @MockitoBean
    private CustomersService service;
    @Autowired
    private MockMvc mockMvc;

    private CustomerDTO customerDTO;
    private CustomerEntity customerEntity;
    private CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        customerDTO = new CustomerDTO(UUID.randomUUID(), "John Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        customerEntity = mapper.toEntity(customerDTO);
    }

}


