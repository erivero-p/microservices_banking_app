package com.talant.bootcamp.accountservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talant.bootcamp.accountservice.controller.AccountServiceController;
import com.talant.bootcamp.accountservice.model.AccountRequest;
import com.talant.bootcamp.accountservice.model.AccountServiceEntity;
import com.talant.bootcamp.accountservice.service.AccountServiceLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountServiceController.class)
class AccountServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountServiceLogic service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAccount_ShouldReturnAccount_WhenValidRequest() throws Exception {
        // Given
        AccountRequest request = new AccountRequest();
        request.setCustomerId(123L);

        AccountServiceEntity expectedAccount = new AccountServiceEntity();
        expectedAccount.setId(1L);
        expectedAccount.setCustomerId(123L);
        expectedAccount.setBalance(BigDecimal.ZERO);

        when(service.createAccount(123L)).thenReturn(expectedAccount);

        // When & Then
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(123L))
                .andExpect(jsonPath("$.balance").value(0));

        verify(service).createAccount(123L);
    }

    @Test
    void getAccount_ShouldReturnAccount_WhenAccountExists() throws Exception {
        // Given
        Long accountId = 1L;
        AccountServiceEntity account = new AccountServiceEntity();
        account.setId(accountId);
        account.setCustomerId(123L);
        account.setBalance(new BigDecimal("100.00"));

        when(service.getAccount(accountId)).thenReturn(account);

        // When & Then
        mockMvc.perform(get("/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(123L))
                .andExpect(jsonPath("$.balance").value(100.00));

        verify(service).getAccount(accountId);
    }

    @Test
    void deposit_ShouldReturnUpdatedAccount_WhenValidAmount() throws Exception {
        // Given
        Long accountId = 1L;
        BigDecimal amount = new BigDecimal("50.00");

        AccountServiceEntity updatedAccount = new AccountServiceEntity();
        updatedAccount.setId(accountId);
        updatedAccount.setBalance(new BigDecimal("150.00"));

        when(service.deposit(accountId, amount)).thenReturn(updatedAccount);

        // When & Then
        mockMvc.perform(patch("/accounts/{id}/deposit", accountId)
                        .param("amount", "50.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.balance").value(150.00));

        verify(service).deposit(accountId, amount);
    }

    @Test
    void withdraw_ShouldReturnUpdatedAccount_WhenValidAmount() throws Exception {
        // Given
        Long accountId = 1L;
        BigDecimal amount = new BigDecimal("25.00");

        AccountServiceEntity updatedAccount = new AccountServiceEntity();
        updatedAccount.setId(accountId);
        updatedAccount.setBalance(new BigDecimal("75.00"));

        when(service.withdraw(accountId, amount)).thenReturn(updatedAccount);

        // When & Then
        mockMvc.perform(patch("/accounts/{id}/withdraw", accountId)
                        .param("amount", "25.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.balance").value(75.00));

        verify(service).withdraw(accountId, amount);
    }
}