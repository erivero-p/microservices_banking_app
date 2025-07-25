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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    private AccountMapper accountMapper;
    private AccountRequest accountRequest;
    private AccountResponse accountResponse;
    
    private Account account1;
    private Account account2;


   @BeforeEach
    void setUp() {

        accountMapper = new AccountMapper() {
            @Override
            public Account toEntity(AccountRequest accountRequest) {
                return new Account(accountRequest.getName(), accountRequest.getAmount());
            }

            @Override
            public AccountResponse toDTO(Account account) {
                return new AccountResponse(account.getId(), account.getName(), account.getAmount());
            }
        };
        accountRequest = new AccountRequest(
                "Test Account",
                100.0
        );

        account1 = new Account(
                1,
                "Test Account",
                100.0
        );


    }

    @Test
    void createAccountSuccess() {
        when(accountService.createAccount(accountRequest)).thenReturn(accountMapper.toDTO(account1));

        try {
            mockMvc.perform(post("/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Test Account\",\"amount\":100.0}"))
                    .andExpect(status().isCreated());
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