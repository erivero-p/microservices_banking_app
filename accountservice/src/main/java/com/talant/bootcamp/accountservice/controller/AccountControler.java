package com.talant.bootcamp.accountservice.controller;


import com.talant.bootcamp.accountservice.dto.AccountDTO;
import com.talant.bootcamp.accountservice.dto.BalanceUpdateRequest;
import com.talant.bootcamp.accountservice.entity.Account;
import com.talant.bootcamp.accountservice.service.AccountService;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/accounts")
public class AccountControler {
    private final AccountService accountService;

    public AccountControler(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Page<AccountDTO> getAccounts(Pageable pageable) {
        return accountService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public AccountDTO getAccount(@PathVariable UUID id) {
        return accountService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createCustomer(@RequestBody AccountDTO dto) {
        AccountDTO created = accountService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deposit")
    public ResponseEntity<AccountDTO> deposit(@PathVariable UUID id, @RequestBody BalanceUpdateRequest request) {
        AccountDTO updated = accountService.adjustBalance(id, request.amount());
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<AccountDTO> withdraw(@PathVariable UUID id, @RequestBody BalanceUpdateRequest request) {
        AccountDTO updated = accountService.adjustBalance(id, request.amount());
        return ResponseEntity.ok(updated);
    }
}
