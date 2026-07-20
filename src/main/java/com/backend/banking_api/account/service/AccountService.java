package com.backend.banking_api.account.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.banking_api.account.entity.Account;
import com.backend.banking_api.account.enums.AccountStatus;
import com.backend.banking_api.account.repository.AccountRepository;
import com.backend.banking_api.branch.entity.Branch;
import com.backend.banking_api.branch.repository.BranchRepository;
import com.backend.banking_api.customer.entity.Customer;
import com.backend.banking_api.customer.repository.CustomerRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository,
                          BranchRepository branchRepository,
                          CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
    }

    public Account create(Long branchId, Long customerId, Account account) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + branchId));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }
        if (account.getStatus() == null) {
            account.setStatus(AccountStatus.ACTIVE);
        }

        account.setBranch(branch);
        account.setCustomer(customer);

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

    public List<Account> findByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
        return accountRepository.findByCustomerId(customerId);
    }

    public Account update(Long id, Account updated) {
        Account existing = findById(id);

        existing.setAccountNumber(updated.getAccountNumber());
        existing.setAccountType(updated.getAccountType());
        existing.setStatus(updated.getStatus());

        return accountRepository.save(existing);
    }

    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }
}
