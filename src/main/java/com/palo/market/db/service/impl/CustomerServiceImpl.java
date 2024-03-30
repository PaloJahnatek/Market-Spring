package com.palo.market.db.service.impl;

import com.palo.market.db.repository.CustomerRepository;
import com.palo.market.db.service.api.CustomerService;
import com.palo.market.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// CustomerServiceImpl sluzi na nejaku biznis logiku
@Service // @service a @component robia v podstate to iste, vytvoria objekt CustomerServiceImpl
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.getAll();
    }

    @Override
    public Customer get(int id) {
        return customerRepository.get(id);
    }

    @Override
    public Integer add(Customer customer) {
        return customerRepository.add(customer);
    }
}
