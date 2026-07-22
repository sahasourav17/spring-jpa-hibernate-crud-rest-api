package com.backend.banking_api.card.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.banking_api.card.dto.CardResponse;
import com.backend.banking_api.card.dto.CreateCardRequest;
import com.backend.banking_api.card.dto.UpdateCardRequest;
import com.backend.banking_api.card.entity.Card;
import com.backend.banking_api.card.repository.CardRepository;
import com.backend.banking_api.customer.entity.Customer;
import com.backend.banking_api.customer.repository.CustomerRepository;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    public CardService(CardRepository cardRepository, CustomerRepository customerRepository) {
        this.cardRepository = cardRepository;
        this.customerRepository = customerRepository;
    }

    public CardResponse createForCustomer(Long customerId, CreateCardRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        if (customer.getCard() != null) {
            throw new RuntimeException("Customer already has a card");
        }

        Card card = new Card();
        card.setCardNumber(request.getCardNumber());
        card.setExpiryDate(request.getExpiryDate());
        card.setCvv(request.getCvv());
        card.setActive(request.getActive() != null ? request.getActive() : true);

        customer.setCard(card);
        customerRepository.save(customer);

        return toResponse(card);
    }

    public List<CardResponse> findAll() {
        return cardRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CardResponse findById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + id));
        return toResponse(card);
    }

    public CardResponse findByCustomerId(Long customerId) {
        Card card = cardRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Card not found for customer id: " + customerId));
        return toResponse(card);
    }

    public CardResponse update(Long id, UpdateCardRequest request) {
        Card existing = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + id));

        existing.setCardNumber(request.getCardNumber());
        existing.setExpiryDate(request.getExpiryDate());
        existing.setCvv(request.getCvv());
        existing.setActive(request.getActive());

        Card saved = cardRepository.save(existing);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new RuntimeException("Card not found with id: " + id);
        }
        cardRepository.deleteById(id);
    }

    private CardResponse toResponse(Card card) {
        Long customerId = card.getCustomer() != null ? card.getCustomer().getId() : null;
        return new CardResponse(
                card.getId(),
                card.getCardNumber(),
                card.getExpiryDate(),
                card.getCvv(),
                card.getActive(),
                customerId,
                card.getCreatedAt(),
                card.getUpdatedAt()
        );
    }
}
