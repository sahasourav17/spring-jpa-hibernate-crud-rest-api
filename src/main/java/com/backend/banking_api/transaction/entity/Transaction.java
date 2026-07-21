package com.backend.banking_api.transaction.entity;

import java.math.BigDecimal;

import com.backend.banking_api.account.entity.Account;
import com.backend.banking_api.common.base.BaseEntity;
import com.backend.banking_api.transaction.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TransactionType type;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(name = "balance_after_txn", nullable = false, precision = 19, scale = 2)
    private BigDecimal balanceAfterTxn;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account account;

    public Transaction() {
    }

    public Transaction(TransactionType type, BigDecimal amount, BigDecimal balanceAfterTxn, String description) {
        this.type = type;
        this.amount = amount;
        this.balanceAfterTxn = balanceAfterTxn;
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfterTxn() {
        return balanceAfterTxn;
    }

    public void setBalanceAfterTxn(BigDecimal balanceAfterTxn) {
        this.balanceAfterTxn = balanceAfterTxn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
