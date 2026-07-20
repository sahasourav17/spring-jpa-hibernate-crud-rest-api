package com.backend.banking_api.card.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public Card createForCustomer(Long customerId, Card card) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customer.getCard() != null) {
            throw new RuntimeException("Customer already has a card");
        }

        customer.setCard(card);
        customerRepository.save(customer);
        return card;
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public Card findById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + id));
    }

    public Card findByCustomerId(Long customerId) {
        return cardRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Card not found for customer id: " + customerId));
    }

    public Card update(Long id, Card updated) {
        Card existing = findById(id);
        existing.setCardNumber(updated.getCardNumber());
        existing.setExpiryDate(updated.getExpiryDate());
        existing.setCvv(updated.getCvv());
        existing.setActive(updated.getActive());
        return cardRepository.save(existing);
    }

    public void delete(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new RuntimeException("Card not found with id: " + id);
        }
        cardRepository.deleteById(id);
    }
}
