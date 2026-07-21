package com.backend.banking_api.transaction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.banking_api.account.entity.Account;
import com.backend.banking_api.account.repository.AccountRepository;
import com.backend.banking_api.transaction.entity.Transaction;
import com.backend.banking_api.transaction.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction createForAccount(Long accountId, Transaction transaction) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    public List<Transaction> findByAccountId(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found with id: " + accountId);
        }
        return transactionRepository.findByAccountId(accountId);
    }

    public Transaction update(Long id, Transaction updated) {
        Transaction existing = findById(id);

        existing.setType(updated.getType());
        existing.setAmount(updated.getAmount());
        existing.setBalanceAfterTxn(updated.getBalanceAfterTxn());
        existing.setDescription(updated.getDescription());

        return transactionRepository.save(existing);
    }

    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }
}
