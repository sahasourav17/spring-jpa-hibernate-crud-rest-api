package com.backend.banking_api.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.banking_api.account.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByBranchId(Long branchId);

    List<Account> findByCustomerId(Long customerId);

    Optional<Account> findByAccountNumber(String accountNumber);
}
