package com.backend.banking_api.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.banking_api.account.entity.Account;
import com.backend.banking_api.account.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/branches/{branchId}/accounts")
    public ResponseEntity<Account> create(@PathVariable Long branchId,
            @Valid @RequestBody Account account) {
        Account created = accountService.createForBranch(branchId, account);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> findAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @GetMapping("/branches/{branchId}/accounts")
    public ResponseEntity<List<Account>> findByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(accountService.findByBranchId(branchId));
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> update(@PathVariable Long id,
            @Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.update(id, account));
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
