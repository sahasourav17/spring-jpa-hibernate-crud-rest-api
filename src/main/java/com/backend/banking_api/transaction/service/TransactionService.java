package com.backend.banking_api.transaction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.banking_api.account.entity.Account;
import com.backend.banking_api.account.repository.AccountRepository;
import com.backend.banking_api.transaction.dto.CreateTransactionRequest;
import com.backend.banking_api.transaction.dto.TransactionResponse;
import com.backend.banking_api.transaction.dto.UpdateTransactionRequest;
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

    public TransactionResponse createForAccount(Long accountId, CreateTransactionRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        Transaction transaction = new Transaction();
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setBalanceAfterTxn(request.getBalanceAfterTxn());
        transaction.setDescription(request.getDescription());
        transaction.setAccount(account);

        Transaction saved = transactionRepository.save(transaction);
        return toResponse(saved);
    }

    public List<TransactionResponse> findAll() {
        return transactionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public TransactionResponse findById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        return toResponse(transaction);
    }

    public List<TransactionResponse> findByAccountId(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found with id: " + accountId);
        }
        return transactionRepository.findByAccountId(accountId).stream()
                .map(this::toResponse)
                .toList();
    }

    public TransactionResponse update(Long id, UpdateTransactionRequest request) {
        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        existing.setType(request.getType());
        existing.setAmount(request.getAmount());
        existing.setBalanceAfterTxn(request.getBalanceAfterTxn());
        existing.setDescription(request.getDescription());

        Transaction saved = transactionRepository.save(existing);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }

    private TransactionResponse toResponse(Transaction transaction) {
        Long accountId = transaction.getAccount() != null ? transaction.getAccount().getId() : null;
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalanceAfterTxn(),
                transaction.getDescription(),
                accountId,
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
}
