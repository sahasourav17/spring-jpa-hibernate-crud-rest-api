package com.backend.banking_api.branch.entity;

import java.util.ArrayList;
import java.util.List;

import com.backend.banking_api.account.entity.Account;
import com.backend.banking_api.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
        account.setBranch(this);
    }
}
