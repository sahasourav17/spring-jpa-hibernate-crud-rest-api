package com.backend.banking_api.card.entity;

import java.time.LocalDate;

import com.backend.banking_api.common.base.BaseEntity;
import com.backend.banking_api.customer.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cards")
public class Card extends BaseEntity{

    @NotBlank
    @Size(max = 16)
    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @NotBlank
    @Size(min = 3, max = 4)
    @Column(name = "cvv", nullable = false, length = 4)
    private String cvv;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    @JsonIgnore
    private Customer customer;


    public Card(){
    }

    public Card(String cardNumber, LocalDate expiryDate, String cvv, Boolean active){
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.active = active;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public LocalDate getExpiryDate(){
        return expiryDate;
    }

    public String getCvv(){
        return cvv;
    }

    public Boolean getActive(){
        return active;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public void setExpiryDate(LocalDate expiryDate){
        this.expiryDate = expiryDate;
    }

    public void setCvv(String cvv){
        this.cvv = cvv;
    }

    public void setActive(Boolean active){
        this.active = active;
    }

    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }
}
