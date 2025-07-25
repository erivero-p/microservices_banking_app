package com.talant.bootcamp.accountservice.service;

import com.talant.bootcamp.accountservice.repository.AccountServiceRepo;
import com.talant.bootcamp.accountservice.model.AccountServiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceLogic {

    @Autowired
    private final AccountServiceRepo repository;

    public AccountServiceLogic(AccountServiceRepo repository) {
        this.repository = repository;
    }

    public AccountServiceEntity createAccount(Long customerId) {
        AccountServiceEntity account = new AccountServiceEntity();
        account.setCustomerId(customerId);
        account.setBalance(BigDecimal.ZERO);
        return repository.save(account);
    }

    public AccountServiceEntity getAccount(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public AccountServiceEntity deposit(Long id, BigDecimal amount) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("Deposit must be positive");
        AccountServiceEntity acc = getAccount(id);
        acc.setBalance(acc.getBalance().add(amount));
        return repository.save(acc);
    }

    public AccountServiceEntity withdraw(Long id, BigDecimal amount) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("Withdraw must be positive");
        AccountServiceEntity acc = getAccount(id);
        if (acc.getBalance().compareTo(amount) < 0)
            throw new IllegalArgumentException("Insufficient funds");
        acc.setBalance(acc.getBalance().subtract(amount));
        return repository.save(acc);
    }

    public AccountServiceEntity withDrawToOther(Long id, BigDecimal amount, Long SecondId){
        if(amount.signum() <= 0) throw new IllegalArgumentException("WITH DRAW MUST BE POSITIVE");
        AccountServiceEntity sender = getAccount(id);
        AccountServiceEntity reciber = getAccount(SecondId);
        if(sender.getBalance().compareTo(amount)<0)
            throw new IllegalArgumentException("Insufficient funds");
        sender.setBalance(sender.getBalance().subtract(amount));
        reciber.setBalance(reciber.getBalance().add(amount));
        repository.save(sender);
        return repository.save(reciber);
    }
}