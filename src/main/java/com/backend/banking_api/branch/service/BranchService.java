package com.backend.banking_api.branch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.banking_api.branch.entity.Branch;
import com.backend.banking_api.branch.repository.BranchRepository;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    // create branch
    public Branch create(Branch branch) {
        return branchRepository.save(branch);
    }

    // get all branches
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    // get branch by id
    public Branch findById(Long id) {
        return branchRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    // update branch
    public Branch update(Long id, Branch updatedBranch) {
        Branch existingBranch = findById(id);
        existingBranch.setName(updatedBranch.getName());
        existingBranch.setBranchCode(updatedBranch.getBranchCode());
        existingBranch.setAddress(updatedBranch.getAddress());
        existingBranch.setCity(updatedBranch.getCity());
        return branchRepository.save(existingBranch);
    }

    // delete branch
    public void delete(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new RuntimeException("Branch not found with id: " + id);
        }
        branchRepository.deleteById(id);
    }

}
