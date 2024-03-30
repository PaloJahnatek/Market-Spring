package com.palo.market.controller;

import com.palo.market.db.service.api.CustomerAccountService;
import com.palo.market.db.service.api.CustomerService;
import com.palo.market.domain.Customer;
import com.palo.market.domain.CustomerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerAccountService customerAccountService;



    @Autowired
    public CustomerController(CustomerService customerService, CustomerAccountService customerAccountService) {
        this.customerService = customerService;
        this.customerAccountService = customerAccountService;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Customer customer) {
        Integer id = customerService.add(customer);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Customer customer = customerService.get(id);
        if (customer == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity getAll() {
        List<Customer> customersList = customerService.getCustomers();
        return new ResponseEntity<>(customersList, HttpStatus.OK);
    }

    @PostMapping("/account")
    public ResponseEntity addAccount(@RequestBody CustomerAccount customerAccount) {
        customerAccountService.addCustomerAccount(customerAccount);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
