package com.talant.bootcamp.accountservice.service;

import com.talant.bootcamp.accountservice.dto.AccountDTO;
import com.talant.bootcamp.accountservice.entity.Account;
import com.talant.bootcamp.accountservice.entity.AccountState;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private AccountDTO request;
    private Account account;

    @BeforeEach
    void setAccount(){
      request = new AccountDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), AccountState.ACTIVE, 0L);

      account = new Account(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), AccountState.ACTIVE, 0L);
    }

    @Test
    void shouldFindAllAccounts() {

        Pageable pageable = PageRequest.of(0, 1, Sort.by("name").ascending());
        Page<Account> accountPage = new PageImpl<>(List.of(account));

        when(accountRepository.findAll(pageable)).thenReturn(accountPage);
        when(accountRepository.save(any(Account.class))).thenReturn(account);


        Page <AccountDTO> result = accountService.findAll(pageable);

        //assertEquals(1, result.getSize());
        assertEquals(account.getId(), result.getContent().getFirst().id());

    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void adjustBalance() {
    }
}