package com.backend.banking_api.branch.entity;

import com.backend.banking_api.common.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "branches")
public class Branch extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "branch_code", nullable = false, unique = true)
    private String branchCode;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    // no arg constructor
    public Branch() {
    }

    // all arg constructor
    public Branch(String name, String branchCode, String address, String city) {
        this.name = name;
        this.branchCode = branchCode;
        this.address = address;
        this.city = city;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
