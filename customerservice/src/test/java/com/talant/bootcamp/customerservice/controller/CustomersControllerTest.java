package com.talant.bootcamp.customerservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.talant.bootcamp.customerservice.exception.BadRequestException;
import com.talant.bootcamp.customerservice.mapper.CustomerMapper;
import com.talant.bootcamp.customerservice.mapper.CustomerMapperImpl;
import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import com.talant.bootcamp.customerservice.service.CustomersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@WebMvcTest(CustomersController.class)
@DisplayName("Book Controller Tests with WebMvcTest")
public class CustomersControllerTest {

    @MockitoBean
    private CustomersService service;
    @Autowired
    private MockMvc mockMvc;

    private CustomerDTO customerDTO;
    private CustomerEntity customerEntity;
    private CustomerMapper mapper = new CustomerMapperImpl();
    private CustomerDTO customerToShow;

    //Object mapper
    //It's needed to add a module to turn correctly into date
    private ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();


    @BeforeEach
    void setUp() {
        customerDTO = new CustomerDTO(UUID.randomUUID(), "John Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        customerEntity = mapper.toEntity(customerDTO);
        customerToShow = mapper.toDtoToShow(customerEntity);
    }

    @Test
    @DisplayName("Should create a new customer succesfully.")
    void shouldCreateCustomer() throws Exception {
        //Given
        when(service.createCustomer(any(CustomerDTO.class))).thenReturn(customerToShow);

        //When & Then
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerToShow))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customerDTO.id().toString())) //UUID type, so it's needed to turn into string
                .andExpect(jsonPath("$.name").value(customerDTO.name()))
                .andExpect(jsonPath("$.email").value(customerDTO.email()))
                .andExpect(jsonPath("$.birthday").value(customerDTO.birthday().toString()));
    }
    @Test
    @DisplayName("The customer email is already in use. A new customer must not be created.")
    void customerEmailAlreadyInUse() throws Exception {
        //Given
        when(service.createCustomer(any(CustomerDTO.class))).thenThrow(BadRequestException.class);

        //When & Then
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerToShow))
                )
                .andExpect(status().isBadRequest());
    }
    //TEST UNDER CONSTRUCTION (It's neededed to send the exception BadRequest when the birthday validation fails. Then this test could be created
    /*
    @Test
    @DisplayName("A customer under age is sended. A new customer must not be created.")
    void customerUnderAge() throws Exception {
        //Given
        when(service.createCustomer(any(CustomerDTO.class))).thenThrow();

        //When & Then
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerToShow))
                )
                .andExpect(status().isBadRequest());
    }
*/
}


