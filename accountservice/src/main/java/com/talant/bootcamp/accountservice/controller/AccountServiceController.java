package com.talant.bootcamp.accountservice.controller;

import com.talant.bootcamp.accountservice.model.AccountRequest;
import com.talant.bootcamp.accountservice.model.AccountServiceEntity;
import com.talant.bootcamp.accountservice.service.AccountServiceLogic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountServiceController {

    private final AccountServiceLogic service;

    public AccountServiceController(AccountServiceLogic service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccountServiceEntity> createAccount(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(service.createAccount(request.getCustomerId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountServiceEntity> getAccount(@PathVariable ("id") Long id) {
        return ResponseEntity.ok(service.getAccount(id));
    }

    @PatchMapping("/{id}/deposit")
    public ResponseEntity<AccountServiceEntity> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(service.deposit(id, amount));
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<AccountServiceEntity> withdraw(@PathVariable ("id") Long id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(service.withdraw(id, amount));
    }

    @PatchMapping("{id}/withdraw/Other")
    public ResponseEntity<AccountServiceEntity> withdrawNext(@PathVariable ("id") Long id, @RequestParam Long secondId,@RequestParam BigDecimal amount){
        return ResponseEntity.ok(service.withDrawToOther(id,amount,secondId));
    }
}
