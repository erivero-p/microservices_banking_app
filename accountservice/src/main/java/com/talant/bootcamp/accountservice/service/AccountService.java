package com.talant.bootcamp.accountservice.service;

import com.talant.bootcamp.accountservice.dto.AccountDTO;
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

    public AccountDTO createAccount(@Valid AccountDTO accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        return accountMapper.toDTO(
                accountRepository.save(account));
    }

    public AccountDTO getAccount(Integer id) {
        Account account =  accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found."));
        return accountMapper.toDTO(account);
    }

    public AccountDTO deposit(Integer id, Double amount) {
        Account account =  accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found."));
        account.setAmount(account.getAmount() + amount);
        return accountMapper.toDTO(accountRepository.save(account));
    }

    public AccountDTO withdraw(Integer id, Double amount) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Account not found."));
        account.setAmount(account.getAmount() - amount);
        return accountMapper.toDTO(accountRepository.save(account));
    }
}
