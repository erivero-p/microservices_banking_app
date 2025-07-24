package com.talant.bootcamp.accountservice.controller;

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
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    private AccountMapper accountMapper;
    private AccountRequest AccountRequest;
    private AccountResponse accountResponse;
    
    private Account account1;
    private Account account2;


   @BeforeEach
    void setUp() {
        // Initialize any necessary components or mock objects here
        account1 = new Account();
        account1.setId(1);
        account1.setName("Test Account 1");
        account1.setAmount(100.0);
        account2 = new Account();
        account2.setId(2);
        account2.setName("Test Account 2");
        account2.setAmount(200.0);


    }

    @Test
    void createAccountSuccess() {
        
        // Arrange
        AccountRequest newAccountRequest = new AccountRequest(3, "New Account", 300.0);
        Account newAccount = new Account();
        newAccount.setId(newAccountRequest.getId());
        newAccount.setName(newAccountRequest.getName());
        newAccount.setAmount(newAccountRequest.getAmount());

        // Mock the service call
        when(accountService.createAccount(newAccountRequest)).thenReturn(newAccountRequest);
        
        // Act & Assert
        try {
            mockMvc.perform(post("/accounts/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"id\":3,\"name\":\"New Account\",\"amount\":300.0}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail("Exception occurred while testing createAccount: " + e.getMessage());
        }

       
    }

    @Test
    void createAccountAlreadyExists() {
    }

    @Test
    void getAccount() {
    }

    @Test
    void depositToAccount() {
    }

    @Test
    void withdrawFromAccount() {
    }
}