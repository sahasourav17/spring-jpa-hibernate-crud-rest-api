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

import com.backend.banking_api.card.dto.CardResponse;
import com.backend.banking_api.card.dto.CreateCardRequest;
import com.backend.banking_api.card.dto.UpdateCardRequest;
import com.backend.banking_api.card.service.CardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/customers/{customerId}/cards")
    public ResponseEntity<CardResponse> create(@PathVariable Long customerId,
                                               @Valid @RequestBody CreateCardRequest request) {
        CardResponse created = cardService.createForCustomer(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/cards")
    public ResponseEntity<List<CardResponse>> findAll() {
        return ResponseEntity.ok(cardService.findAll());
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<CardResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.findById(id));
    }

    @GetMapping("/customers/{customerId}/cards")
    public ResponseEntity<CardResponse> findByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(cardService.findByCustomerId(customerId));
    }

    @PutMapping("/cards/{id}")
    public ResponseEntity<CardResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UpdateCardRequest request) {
        return ResponseEntity.ok(cardService.update(id, request));
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
