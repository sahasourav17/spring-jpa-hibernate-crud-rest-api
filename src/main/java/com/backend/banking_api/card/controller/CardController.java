package com.backend.banking_api.card.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.banking_api.card.entity.Card;
import com.backend.banking_api.card.service.CardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // Create card for a customer (One-to-One)
    @PostMapping("/customers/{customerId}/cards")
    public ResponseEntity<Card> create(@PathVariable Long customerId,
            @Valid @RequestBody Card card) {
        Card created = cardService.createForCustomer(customerId, card);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/cards")
    public ResponseEntity<List<Card>> findAll() {
        return ResponseEntity.ok(cardService.findAll());
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.findById(id));
    }

    @GetMapping("/customers/{customerId}/cards")
    public ResponseEntity<Card> findByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(cardService.findByCustomerId(customerId));
    }

    @PutMapping("/cards/{id}")
    public ResponseEntity<Card> update(@PathVariable Long id, @Valid @RequestBody Card card) {
        return ResponseEntity.ok(cardService.update(id, card));
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}