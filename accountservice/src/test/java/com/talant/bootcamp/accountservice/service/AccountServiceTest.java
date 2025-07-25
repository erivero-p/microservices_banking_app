package com.talant.bootcamp.accountservice.service;

import com.talant.bootcamp.accountservice.dto.AccountDTO;
import com.talant.bootcamp.accountservice.entity.Account;
import com.talant.bootcamp.accountservice.entity.AccountState;
import com.talant.bootcamp.accountservice.mapper.AccountMapper;
import com.talant.bootcamp.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    private AccountDTO request;
    private Account account;

    @BeforeEach
    void setAccount(){
      request = new AccountDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), AccountState.ACTIVE, 0L);

      account = new Account(request.id(), request.customerId(), LocalDate.now(), AccountState.ACTIVE, 0L);
    }

    @Test
    void shouldFindAllAccounts() {

        Pageable pageable = PageRequest.of(0, 1, Sort.by("name").ascending());
        Page<Account> accountPage = new PageImpl<>(List.of(account));

        when(accountRepository.findAll(pageable)).thenReturn(accountPage);
        when(accountMapper.toAccountDTO(account)).thenReturn(request);


        Page <AccountDTO> result = accountService.findAll(pageable);

        assertEquals(1, result.getSize());
        assertEquals(account.getId(), result.getContent().getFirst().id());

    }

    @Test
    void shouldCreateNewAccount() {

        when(accountMapper.toAccountEntity(request)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toAccountDTO(account)).thenReturn(request);

        AccountDTO response = accountService.create(request);

        assertNotNull(response);
        assertEquals(account.getId(), response.id());
        assertEquals(account.getAccountBalance(), response.accountBalance());
        assertEquals(account.getAccountState(), response.accountState());
        assertEquals(account.getCreationDate(), response.creationDate());
        assertEquals(account.getCustomerId(), response.customerId());

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }

    @Test
    void adjustBalance() {
    }
}