package com.backend.banking_api.branch.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.banking_api.branch.dto.BranchResponse;
import com.backend.banking_api.branch.dto.CreateBranchRequest;
import com.backend.banking_api.branch.dto.UpdateBranchRequest;
import com.backend.banking_api.branch.service.BranchService;

import jakarta.validation.Valid;

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
    public ResponseEntity<BranchResponse> createBranch(@Valid @RequestBody CreateBranchRequest request) {
        BranchResponse createdBranch = branchService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    // get all branches
    @GetMapping
    public ResponseEntity<Page<BranchResponse>> getAllBranches(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<BranchResponse> branches = branchService.findAll(pageable);
        return ResponseEntity.ok(branches);
    }

    // get branch by id
    @GetMapping("/{id}")
    public ResponseEntity<BranchResponse> getBranchById(@PathVariable Long id) {
        BranchResponse branch = branchService.findById(id);
        return ResponseEntity.ok(branch);
    }

    // update branch
    @PutMapping("/{id}")
    public ResponseEntity<BranchResponse> updateBranch(@PathVariable Long id,
            @Valid @RequestBody UpdateBranchRequest request) {
        BranchResponse updatedBranchResult = branchService.update(id, request);
        return ResponseEntity.ok(updatedBranchResult);
    }

    // delete branch
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
