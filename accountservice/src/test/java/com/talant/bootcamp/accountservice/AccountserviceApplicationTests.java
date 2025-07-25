package com.talant.bootcamp.accountservice;

import com.talant.bootcamp.accountservice.model.AccountServiceEntity;
import com.talant.bootcamp.accountservice.repository.AccountServiceRepo;
import com.talant.bootcamp.accountservice.service.AccountServiceLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountserviceApplicationTests{

	@Mock
	private AccountServiceRepo repository;

	@InjectMocks
	private AccountServiceLogic service;

	private AccountServiceEntity account;

	@BeforeEach
	void setUp() {
		account = new AccountServiceEntity();
		account.setId(1L);
		account.setCustomerId(10L);
		account.setBalance(new BigDecimal("100.00"));
	}


	@Test
	void shouldCreateAccountWithZeroBalance() {
		when(repository.save(any(AccountServiceEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

		AccountServiceEntity result = service.createAccount(10L);

		assertEquals(10L, result.getCustomerId());
		assertEquals(BigDecimal.ZERO, result.getBalance());
		verify(repository).save(any(AccountServiceEntity.class));
	}


	@Test
	void shouldReturnAccountWhenFound() {
		when(repository.findById(1L)).thenReturn(Optional.of(account));

		AccountServiceEntity result = service.getAccount(1L);

		assertEquals(account, result);
		verify(repository).findById(1L);
	}


	@Test
	void shouldThrowWhenAccountNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> service.getAccount(1L));
	}


	@Test
	void shouldDepositAmount() {
		when(repository.findById(1L)).thenReturn(Optional.of(account));
		when(repository.save(any(AccountServiceEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

		AccountServiceEntity result = service.deposit(1L, new BigDecimal("50.00"));

		assertEquals(new BigDecimal("150.00"), result.getBalance());
	}


	@Test
	void shouldThrowWhenDepositAmountNonPositive() {
		assertThrows(IllegalArgumentException.class, () -> service.deposit(1L, BigDecimal.ZERO));
	}


	@Test
	void shouldWithdrawAmount() {
		when(repository.findById(1L)).thenReturn(Optional.of(account));
		when(repository.save(any(AccountServiceEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

		AccountServiceEntity result = service.withdraw(1L, new BigDecimal("40.00"));

		assertEquals(new BigDecimal("60.00"), result.getBalance());
	}


	@Test
	void shouldThrowWhenWithdrawAmountNonPositive() {
		assertThrows(IllegalArgumentException.class, () -> service.withdraw(1L, BigDecimal.ZERO));
	}


	@Test
	void shouldThrowWhenWithdrawMoreThanBalance() {
		when(repository.findById(1L)).thenReturn(Optional.of(account));

		assertThrows(IllegalArgumentException.class, () -> service.withdraw(1L, new BigDecimal("200.00")));
	}

}