package com.palo.market.db.service.api;

import com.palo.market.domain.Customer;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();

    @Nullable
    Customer get(int id);

    @Nullable
   Integer add(Customer customer); // bude vracat generovane id

}
