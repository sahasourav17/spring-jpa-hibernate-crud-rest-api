package com.backend.banking_api.account.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.banking_api.account.dto.AccountResponse;
import com.backend.banking_api.account.dto.CreateAccountRequest;
import com.backend.banking_api.account.dto.UpdateAccountRequest;
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

    public AccountResponse create(Long branchId, Long customerId, CreateAccountRequest request) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + branchId));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        Account account = new Account();
        account.setAccountNumber(request.getAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBranch(branch);
        account.setCustomer(customer);

        Account saved = accountRepository.save(account);
        return toResponse(saved);
    }

    public Page<AccountResponse> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable).map(this::toResponse);
    }

    public AccountResponse findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return toResponse(account);
    }

    public Page<AccountResponse> findByBranchId(Long branchId, Pageable pageable) {
        if (!branchRepository.existsById(branchId)) {
            throw new RuntimeException("Branch not found with id: " + branchId);
        }
        return accountRepository.findByBranchId(branchId, pageable).map(this::toResponse);
    }

    public Page<AccountResponse> findByCustomerId(Long customerId, Pageable pageable) {
        if (!customerRepository.existsById(customerId)) {
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
        return accountRepository.findByCustomerId(customerId, pageable).map(this::toResponse);
    }

    public AccountResponse update(Long id, UpdateAccountRequest request) {
        Account existing = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        existing.setAccountNumber(request.getAccountNumber());
        existing.setAccountType(request.getAccountType());
        existing.setStatus(request.getStatus());

        Account saved = accountRepository.save(existing);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

    private AccountResponse toResponse(Account account) {
        Long branchId = account.getBranch() != null ? account.getBranch().getId() : null;
        Long customerId = account.getCustomer() != null ? account.getCustomer().getId() : null;

        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                branchId,
                customerId,
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }
}
