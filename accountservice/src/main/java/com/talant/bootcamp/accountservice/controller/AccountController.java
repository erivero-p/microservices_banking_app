package com.talant.bootcamp.accountservice.controller;

import com.talant.bootcamp.accountservice.dto.AccountRequest;
import com.talant.bootcamp.accountservice.dto.AccountResponse;
import com.talant.bootcamp.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(
            @Valid
            @RequestBody AccountRequest AccountRequest) {
        AccountResponse accountResponse = accountService.createAccount(AccountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Integer id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> depositToAccount(@PathVariable Integer id,
                                                       @RequestParam Double amount) {
        return ResponseEntity.ok(accountService.deposit(id, amount));
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdrawFromAccount(@PathVariable Integer id,
                                                       @RequestParam Double amount) {
        return ResponseEntity.ok(accountService.withdraw(id, amount));
    }

}
