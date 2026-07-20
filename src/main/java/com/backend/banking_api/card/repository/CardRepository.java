package com.backend.banking_api.card.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.banking_api.card.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCustomerId(Long customerId);
}
