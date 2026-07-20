package com.backend.banking_api.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.banking_api.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByBranchId(Long branchId);

    Optional<Account> findByAccountNumber(String accountNumber);
}
