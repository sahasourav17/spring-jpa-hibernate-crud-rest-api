package com.backend.banking_api.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.banking_api.customer.entity.Customer;
import com.backend.banking_api.customer.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // create customer
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    // get all customers
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    // get customer by id
    public Customer findById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    // update customer
    public Customer update(Long id, Customer customer) {
        Customer existingCustomer = findById(id);
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());
        return customerRepository.save(existingCustomer);
    }

    // delete customer
    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
