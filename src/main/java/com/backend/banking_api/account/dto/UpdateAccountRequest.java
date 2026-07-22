package com.backend.banking_api.account.dto;

import com.backend.banking_api.account.enums.AccountStatus;
import com.backend.banking_api.account.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateAccountRequest {

    @NotBlank
    @Size(max = 20)
    private String accountNumber;

    @NotNull
    private AccountType accountType;

    @NotNull
    private AccountStatus status;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
