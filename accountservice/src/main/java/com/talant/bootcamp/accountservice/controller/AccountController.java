package com.talant.bootcamp.accountservice.controller;

import com.talant.bootcamp.accountservice.dto.AccountDTO;
import com.talant.bootcamp.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(
            @Valid
            @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(
                accountService.createAccount(accountDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Integer id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDTO> depositToAccount(@PathVariable Integer id,
                                                       @RequestParam Double amount) {
        return ResponseEntity.ok(accountService.deposit(id, amount));
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDTO> withdrawFromAccount(@PathVariable Integer id,
                                                       @RequestParam Double amount) {
        return ResponseEntity.ok(accountService.withdraw(id, amount));
    }

}
