package com.talant.bootcamp.customerservice.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import com.talant.bootcamp.customerservice.repository.CustomersRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private CustomerEntity customerEntity;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp(){
        customersRepository.deleteAll();

         customerEntity = new CustomerEntity();
        customerEntity.setName("Julio");
        customerEntity.setEmail("julito@example.com");
        customerEntity.setBirthday(LocalDate.now().minusYears(18));
        customerEntity.setId(UUID.randomUUID());

        //Set standard customer DTO
         customerDTO = new CustomerDTO(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getBirthday(),
                customerEntity.getEmail()
        );
    }

    @Test
    @DisplayName("Should create a customer")
    void shouldCreateACustomer() throws Exception {
        String createDto = mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(customerDTO.id().toString())) //UUID type, so it's needed to turn into string
                .andExpect(jsonPath("$.name").value(customerDTO.name()))
                .andExpect(jsonPath("$.email").value(customerDTO.email()))
                .andExpect(jsonPath("$.birthday").value(customerDTO.birthday().toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CustomerDTO customerDTO1 = objectMapper.readValue(createDto,CustomerDTO.class);

        mockMvc.perform(get("/api/customers/"+customerDTO1.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerDTO1.id()))
                .andExpect(jsonPath("$.name").value(customerDTO1.name()));

    }





}
