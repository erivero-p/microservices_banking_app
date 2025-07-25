package com.talant.bootcamp.accountservice.service;

import com.talant.bootcamp.accountservice.dto.AccountRequest;
import com.talant.bootcamp.accountservice.dto.AccountResponse;
import com.talant.bootcamp.accountservice.entity.Account;
import com.talant.bootcamp.accountservice.mapper.AccountMapper;
import com.talant.bootcamp.accountservice.repository.AccountRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private AccountMapper accountMapper;
    private AccountRepository accountRepository;

    public AccountResponse createAccount(@Valid AccountRequest accountRequest) {
        Account account = accountMapper.toEntity(accountRequest);
        //if account already exists, throw an exception
        if( accountRepository.existsById(account.getId())){
            throw new RuntimeException("Account already exists.");
        }
        return accountMapper.toDTO(
                accountRepository.save(account));
    }

    public AccountResponse getAccount(Integer id) {
        Account account =  accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found."));
        return accountMapper.toDTO(account);
    }

    public AccountResponse deposit(Integer id, Double amount) {
        Account account =  accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found."));
        if (amount <= 0) {
            throw new RuntimeException("Deposit amount must be greater than zero.");
        }
        account.setAmount(account.getAmount() + amount);
        return accountMapper.toDTO(accountRepository.save(account));
    }

    public AccountResponse withdraw(Integer id, Double amount) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found."));
        if (account.getAmount() < amount) {
            throw new RuntimeException("Insufficient funds.");
        }
        account.setAmount(account.getAmount() - amount);
        return accountMapper.toDTO(accountRepository.save(account));
    }
}
