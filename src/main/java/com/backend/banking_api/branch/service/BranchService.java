package com.backend.banking_api.branch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.banking_api.branch.dto.BranchResponse;
import com.backend.banking_api.branch.dto.CreateBranchRequest;
import com.backend.banking_api.branch.dto.UpdateBranchRequest;
import com.backend.banking_api.branch.entity.Branch;
import com.backend.banking_api.branch.repository.BranchRepository;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public BranchResponse create(CreateBranchRequest request) {
        Branch branch = new Branch();
        branch.setName(request.getName());
        branch.setBranchCode(request.getBranchCode());
        branch.setCity(request.getCity());
        branch.setAddress(request.getAddress());

        Branch saved = branchRepository.save(branch);
        return toResponse(saved);
    }

    public Page<BranchResponse> findAll(Pageable pageable) {
        return branchRepository.findAll(pageable).map(this::toResponse);
    }

    public BranchResponse findById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
        return toResponse(branch);
    }

    public BranchResponse update(Long id, UpdateBranchRequest request) {
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));

        existing.setName(request.getName());
        existing.setBranchCode(request.getBranchCode());
        existing.setCity(request.getCity());
        existing.setAddress(request.getAddress());

        Branch saved = branchRepository.save(existing);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new RuntimeException("Branch not found with id: " + id);
        }
        branchRepository.deleteById(id);
    }

    private BranchResponse toResponse(Branch branch) {
        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getBranchCode(),
                branch.getCity(),
                branch.getAddress(),
                branch.getCreatedAt(),
                branch.getUpdatedAt()
        );
    }
}