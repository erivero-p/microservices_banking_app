package com.talant.bootcamp.accountservice.service;


import com.talant.bootcamp.accountservice.dto.AccountRequest;
import com.talant.bootcamp.accountservice.dto.AccountResponse;
import com.talant.bootcamp.accountservice.entity.Account;
import com.talant.bootcamp.accountservice.mapper.AccountMapper;
import com.talant.bootcamp.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//add any here


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper; // Assuming you have an AccountMapper for mapping DTOs to entities

    @InjectMocks
    private AccountService accountService;

    private AccountRequest accountRequest;
    private AccountResponse accountResponse;
    private Account account1;

    @BeforeEach
    void setUp() {


        accountRequest = new AccountRequest("Test Account", 100.0);
        account1 = new Account("Test Account", 100.0);
        account1.setId(1);
        accountResponse = new AccountResponse(account1);
    }

    @Test
    void createAccount_ShouldReturnAccountResponse_WhenAccountIsCreated() {
        // Given
        when(accountMapper.toEntity(any(AccountRequest.class))).thenReturn(account1);
        when(accountMapper.toDTO(any(Account.class))).thenReturn(accountResponse);
        when(accountRepository.existsById(any(Integer.class))).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(account1);

        // When
        AccountResponse response = accountService.createAccount(accountRequest);

        // Then
        assertNotNull(response);
        assertEquals(account1.getId(), response.getId());
        assertEquals(account1.getName(), response.getName());
        assertEquals(account1.getAmount(), response.getAmount());
    }
}
