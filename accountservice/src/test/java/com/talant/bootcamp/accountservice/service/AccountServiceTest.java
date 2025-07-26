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
import java.util.Optional;
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
        when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));
        when(accountMapper.toAccountDTO(account)).thenReturn(request);

        AccountDTO response = accountService.findById(account.getId());

        assertNotNull(response);
        assertEquals(account.getId(), response.id());
        assertEquals(account.getAccountBalance(), response.accountBalance());
        assertEquals(account.getAccountState(), response.accountState());
        assertEquals(account.getCreationDate(), response.creationDate());
        assertEquals(account.getCustomerId(), response.customerId());
    }

    @Test
    void findByIdDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.findById(id);
        });

        assertEquals("Customer not found: " + id, exception.getMessage());
    }

    @Test
    void shouldDeleteAccountWhenExists() {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        accountService.delete(account.getId());

        verify(accountRepository).delete(account);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingAccount() {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.delete(account.getId()));

        assertEquals("Customer not found: " + account.getId(), exception.getMessage());
    }

    @Test
    void shouldAdjustBalanceSuccessfully() {
        UUID id = account.getId();
        long adjustment = 500L;

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        Account updatedAccount = new Account(account.getId(), account.getCustomerId(), account.getCreationDate(), account.getAccountState(), account.getAccountBalance() + adjustment);
        when(accountRepository.save(account)).thenReturn(updatedAccount);

        when(accountMapper.toAccountDTO(updatedAccount)).thenReturn(request);

        AccountDTO result = accountService.adjustBalance(id, adjustment);

        assertNotNull(result);
        assertEquals(request.id(), result.id());
        verify(accountRepository).save(account);
        assertEquals(500L,
                account.getAccountBalance());
    }

    @Test
    void shouldThrowExceptionWhenAdjustingBalanceForNonExistingAccount() {
        UUID id = UUID.randomUUID();

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.adjustBalance(id, 100));

        assertEquals("Customer not found: " + id, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInsufficientFunds() {
        UUID id = account.getId();
        account.setAccountBalance(100L); // Set current balance low
        long amountToDeduct = -200L;

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.adjustBalance(id, amountToDeduct);
        });

        assertEquals("Insufficient funds for account: " + id, exception.getMessage());
    }



}