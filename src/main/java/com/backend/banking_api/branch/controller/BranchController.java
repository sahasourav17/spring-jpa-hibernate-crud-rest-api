package com.backend.banking_api.branch.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.banking_api.branch.entity.Branch;
import com.backend.banking_api.branch.service.BranchService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // create branch
    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
        Branch createdBranch = branchService.create(branch);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    // get all branches
    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> branches = branchService.findAll();
        return ResponseEntity.ok(branches);
    }

    // get branch by id
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        Branch branch = branchService.findById(id);
        return ResponseEntity.ok(branch);
    }

    // update branch
    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody Branch updatedBranch) {
        Branch updatedBranchResult = branchService.update(id, updatedBranch);
        return ResponseEntity.ok(updatedBranchResult);
    }

    // delete branch
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
