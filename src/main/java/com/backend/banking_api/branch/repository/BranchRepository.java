package com.backend.banking_api.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.banking_api.branch.entity.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

}
