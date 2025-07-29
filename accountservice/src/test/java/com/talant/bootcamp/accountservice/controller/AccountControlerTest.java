package com.talant.bootcamp.accountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.talant.bootcamp.accountservice.dto.AccountDTO;
import com.talant.bootcamp.accountservice.entity.Account;
import com.talant.bootcamp.accountservice.entity.AccountState;
import com.talant.bootcamp.accountservice.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountControler.class)
@DisplayName("Account Controller Tests with WebMvcTest")
class AccountControlerTest {

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    private AccountDTO accountDTO;
    private Account account;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        accountDTO = new AccountDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), AccountState.ACTIVE, 0L);

        account = new Account(accountDTO.id(), accountDTO.customerId(), LocalDate.now(), AccountState.ACTIVE, 0L);
    }

    @Test
    void getAccounts() throws Exception{
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10); // Assuming default pagination
        Page<AccountDTO> accountPage = new PageImpl<>(List.of(accountDTO));

        when(accountService.findAll(pageable)).thenReturn(accountPage);

        // WHEN & THEN
        mockMvc.perform(get("/api/v1/accounts")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(accountDTO.id().toString()))
                .andExpect(jsonPath("$.content[0].customerId").value(accountDTO.customerId().toString()))
                .andExpect(jsonPath("$.content[0].accountBalance").value(0))
                .andExpect(jsonPath("$.content[0].accountState").value("ACTIVE"));
    }

    @Test
    void getExistingAccount() throws Exception{
        // GIVEN
        when(accountService.findById(accountDTO.id())).thenReturn(accountDTO);

        // WHEN & THEN
        mockMvc.perform(get("/api/v1/accounts/{id}", accountDTO.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountDTO.id().toString()))
                .andExpect(jsonPath("$.customerId").value(accountDTO.customerId().toString()))
                .andExpect(jsonPath("$.accountBalance").value(0))
                .andExpect(jsonPath("$.accountState").value("ACTIVE"));

    }

    @Test
    void createCustomer() throws Exception{
        // GIVEN
        when(accountService.create(accountDTO)).thenReturn(accountDTO);

        // WHEN & THEN
        mockMvc.perform(post("/api/v1/accounts", accountDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(accountDTO.id().toString()))
                .andExpect(jsonPath("$.customerId").value(accountDTO.customerId().toString()))
                .andExpect(jsonPath("$.accountBalance").value(0))
                .andExpect(jsonPath("$.accountState").value("ACTIVE"));
    }

    @Test
    void deleteAccount() throws Exception {
        mockMvc.perform(delete("/api/v1/accounts/{id}", accountDTO.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deposit() throws Exception {
        long depositAmount = 100L;
        AccountDTO updatedDTO = new AccountDTO(
                accountDTO.id(),
                accountDTO.customerId(),
                accountDTO.creationDate(),
                accountDTO.accountState(),
                accountDTO.accountBalance() + depositAmount
        );

        when(accountService.adjustBalance(accountDTO.id(), depositAmount)).thenReturn(updatedDTO);

        mockMvc.perform(patch("/api/v1/accounts/{id}/deposit", accountDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": " + depositAmount + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountDTO.id().toString()))
                .andExpect(jsonPath("$.accountBalance").value(depositAmount));
    }

    @Test
    void withdraw() throws Exception {
        long withdrawalAmount = -50L;
        AccountDTO updatedDTO = new AccountDTO(
                accountDTO.id(),
                accountDTO.customerId(),
                accountDTO.creationDate(),
                accountDTO.accountState(),
                accountDTO.accountBalance() + withdrawalAmount
        );

        when(accountService.adjustBalance(accountDTO.id(), withdrawalAmount)).thenReturn(updatedDTO);

        mockMvc.perform(patch("/api/v1/accounts/{id}/withdraw", accountDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": " + withdrawalAmount + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountDTO.id().toString()))
                .andExpect(jsonPath("$.accountBalance").value(accountDTO.accountBalance() + withdrawalAmount));
    }
}