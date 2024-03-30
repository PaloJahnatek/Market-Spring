package com.palo.market.db.service.impl;

import com.palo.market.db.repository.CustomerAccountRepository;
import com.palo.market.db.service.api.CustomerAccountService;
import com.palo.market.domain.CustomerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;

    @Autowired
    public CustomerAccountServiceImpl(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public void addCustomerAccount(CustomerAccount customerAccount) {
        customerAccountRepository.add(customerAccount);
    }

    @Override
    public Double getMoney(int customerId) {
        return customerAccountRepository.getMoney(customerId);
    }

    @Override
    public void setMoney(int customerId, double money) {
        customerAccountRepository.setMoney(customerId, money);
    }
}
