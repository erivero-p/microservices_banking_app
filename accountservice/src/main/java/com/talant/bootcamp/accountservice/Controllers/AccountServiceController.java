package com.talant.bootcamp.accountservice.Controllers;

import com.talant.bootcamp.accountservice.Service.AccountServiceEntity;
import com.talant.bootcamp.accountservice.Service.AccountServiceLogic;
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
    public ResponseEntity<AccountServiceEntity> createAccount(@RequestParam Long customerId) {
        return ResponseEntity.ok(service.createAccount(customerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountServiceEntity> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAccount(id));
    }

    @PatchMapping("/{id}/deposit")
    public ResponseEntity<AccountServiceEntity> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(service.deposit(id, amount));
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<AccountServiceEntity> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(service.withdraw(id, amount));
    }
}
