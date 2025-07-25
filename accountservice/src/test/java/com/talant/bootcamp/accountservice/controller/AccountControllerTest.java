package com.talant.bootcamp.accountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talant.bootcamp.accountservice.dto.AccountRequest;
import com.talant.bootcamp.accountservice.dto.AccountResponse;
import com.talant.bootcamp.accountservice.entity.Account;
import com.talant.bootcamp.accountservice.mapper.AccountMapper;
import com.talant.bootcamp.accountservice.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private AccountRequest accountRequest;
    private AccountResponse accountResponse;
    
    private Account account1;
    private Account account2;


   @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();
        accountRequest = new AccountRequest(
                "Test Account",
                100.0
        );

        account1 = new Account(
                "Test Account",
                100.0
        );
        account1.setId(1);

        accountResponse = new AccountResponse(account1);


    }

    @Test
    void createAccountSuccess() throws Exception {
        when(accountService.createAccount(any(AccountRequest.class))).thenReturn(accountResponse);

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath( "$.name").value(account1.getName()))
                .andExpect(jsonPath("$.amount").value(account1.getAmount()))
                .andExpect(jsonPath("$.id").value(account1.getId()))
                .andReturn();
    }

    @Test
    void createAccountAlreadyExists() throws Exception{

        when(accountService.createAccount(any(AccountRequest.class)))
                .thenThrow(new RuntimeException("Account already exists."));

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isConflict())
                .andReturn();


    }

    @Test
    void getAccount() throws Exception {
        when(accountService.getAccount(1)).thenReturn(accountResponse);


        mockMvc.perform(get("/accounts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(account1.getName()))
                .andExpect(jsonPath("$.amount").value(account1.getAmount()))
                .andExpect(jsonPath("$.id").value(account1.getId()))
                .andReturn();


    }
    @Test
    void accountNotFound() throws Exception {
        when(accountService.getAccount(1))
                .thenThrow(new RuntimeException("Account not found."));

        mockMvc.perform(get("/accounts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account not found."))
                .andReturn();
    }

    @Test
    void depositToAccount() throws Exception {
        AccountResponse updatedAccountResponse = new AccountResponse(
                new Account(1,"Test Account", 150.0)
        );
        when(accountService.deposit(eq(1), any(Double.class))).thenReturn(updatedAccountResponse);

        mockMvc.perform(put("/accounts/1/deposit")
                .param("amount", "50.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(account1.getName()))
                .andExpect(jsonPath("$.amount").value(150.0))
                .andExpect(jsonPath("$.id").value(account1.getId()))
                .andReturn();

    }

    @Test
    void cannotDepositNegativeAmount() throws Exception {
        when(accountService.deposit(eq(1), any(Double.class)))
                .thenThrow(new RuntimeException("Deposit amount must be greater than zero."));

        mockMvc.perform(put("/accounts/1/deposit")
                .param("amount", "-50.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Deposit amount must be greater than zero."))
                .andReturn();
    }

    @Test
    void withdrawFromAccount() throws Exception{
        AccountResponse updatedAccountResponse = new AccountResponse(
                new Account(1,"Test Account", 50.0)
        );
        when(accountService.withdraw(eq(1), any(Double.class))).thenReturn(updatedAccountResponse);


        mockMvc.perform(put("/accounts/1/withdraw")
                .param("amount", "50.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(account1.getName()))
                .andExpect(jsonPath("$.amount").value(50.0))
                .andExpect(jsonPath("$.id").value(account1.getId()))
                .andReturn();

    }

    @Test
    void cannotWithdrawMoreThanBalance() throws Exception {
        when(accountService.withdraw(eq(1), any(Double.class)))
                .thenThrow(new RuntimeException("Insufficient funds."));

        mockMvc.perform(put("/accounts/1/withdraw")
                .param("amount", "200.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds."))
                .andReturn();
    }

    @Test
    void badFormedRequest() throws Exception {
        // Simulate a bad request with missing fields
        AccountRequest badRequest = new AccountRequest(null, -50.0);

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}