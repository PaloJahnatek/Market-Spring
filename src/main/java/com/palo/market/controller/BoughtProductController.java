package com.palo.market.controller;

import com.palo.market.db.service.api.BoughtProductService;
import com.palo.market.domain.BoughtProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bought-product") // bought-product je prefix
public class BoughtProductController {

    private final BoughtProductService boughtProductService;

    @Autowired
    public BoughtProductController(BoughtProductService boughtProductService) {
        this.boughtProductService = boughtProductService;
    }

    @GetMapping("{customerId}")
    public ResponseEntity getByCustomerId(@PathVariable("customerId") int customerId) {
        List<BoughtProduct> boughtProductList = boughtProductService.getAllByCustomerId(customerId);
        return new ResponseEntity<>(boughtProductList, HttpStatus.OK);

    }
}
