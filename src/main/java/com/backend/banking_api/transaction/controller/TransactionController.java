package com.backend.banking_api.transaction.controller;

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

import com.backend.banking_api.transaction.entity.Transaction;
import com.backend.banking_api.transaction.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<Transaction> create(@PathVariable Long accountId,
                                              @Valid @RequestBody Transaction transaction) {
        Transaction created = transactionService.createForAccount(accountId, transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> findAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> findByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.findByAccountId(accountId));
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id,
                                              @Valid @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.update(id, transaction));
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
