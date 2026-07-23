package com.backend.banking_api.account.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.banking_api.account.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findByBranchId(Long branchId, Pageable pageable);

    Page<Account> findByCustomerId(Long customerId, Pageable pageable);

    Optional<Account> findByAccountNumber(String accountNumber);
}
