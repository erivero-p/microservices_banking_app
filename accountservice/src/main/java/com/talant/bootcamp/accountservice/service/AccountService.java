package com.talant.bootcamp.accountservice.service;

import com.talant.bootcamp.accountservice.dto.AccountDTO;
import com.talant.bootcamp.accountservice.entity.Account;
import com.talant.bootcamp.accountservice.mapper.AccountMapper;
import com.talant.bootcamp.accountservice.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public Page<AccountDTO> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable).map(accountMapper::toAccountDTO);
    }

    public AccountDTO findById(UUID id) {
        return accountRepository.findById(id)
                .map(accountMapper::toAccountDTO)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }

    public AccountDTO create(AccountDTO dto) {
        Account entity = accountMapper.toAccountEntity(dto);
        return accountMapper.toAccountDTO(accountRepository.save(entity));
    }

    public void delete(UUID id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException(("Customer not found: " + id))
        );

        accountRepository.delete(account);
    }

    public AccountDTO adjustBalance(UUID id, long amount) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new RuntimeException(("Customer not found: " + id))
        );

        long newBalance = account.getAccountBalance() + amount;

        if (newBalance < 0) {
            throw new IllegalArgumentException("Insufficient funds for account: " + id);
        }

        account.setAccountBalance(newBalance);
        Account updated = accountRepository.save(account);

        return accountMapper.toAccountDTO(updated);
    }

}
