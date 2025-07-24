package com.talant.bootcamp.customerservice.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.talant.bootcamp.customerservice.repository.CustomersRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("Customer integration tests")
public class CustomerIntegrationTest {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired //Ver si así funciona
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        customersRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a customer")
    void shouldCreateACustomer(){

    }





}
