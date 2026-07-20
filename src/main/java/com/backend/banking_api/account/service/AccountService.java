package com.backend.banking_api.account.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.banking_api.account.entity.Account;
import com.backend.banking_api.account.enums.AccountStatus;
import com.backend.banking_api.account.repository.AccountRepository;
import com.backend.banking_api.branch.entity.Branch;
import com.backend.banking_api.branch.repository.BranchRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;

    public AccountService(AccountRepository accountRepository, BranchRepository branchRepository) {
        this.accountRepository = accountRepository;
        this.branchRepository = branchRepository;
    }

    public Account createForBranch(Long branchId, Account account) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }
        if (account.getStatus() == null) {
            account.setStatus(AccountStatus.ACTIVE);
        }
        account.setBranch(branch);
        return accountRepository.save(account);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    public List<Account> findByBranchId(Long branchId) {
        if (!branchRepository.existsById(branchId)) {
            throw new RuntimeException("Branch not found with id: " + branchId);
        }
        return accountRepository.findByBranchId(branchId);
    }

    public Account update(Long id, Account account) {
        Account existing = findById(id);
        existing.setAccountNumber(account.getAccountNumber());
        existing.setAccountType(account.getAccountType());
        existing.setStatus(account.getStatus());
        return accountRepository.save(existing);
    }

    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

}
