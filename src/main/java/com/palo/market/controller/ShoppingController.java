package com.palo.market.controller;

import com.palo.market.db.service.api.ShoppingService;
import com.palo.market.db.service.api.request.BuyProductRequest;
import com.palo.market.db.service.api.response.BuyProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("market")
public class ShoppingController {
    private final ShoppingService shoppingService;

    @Autowired
    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping("/buy")
    public ResponseEntity buy(@RequestBody BuyProductRequest request) {
        BuyProductResponse buyProductResponse = shoppingService.buyProduct(request);
        if (buyProductResponse.isSuccess()) {
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(buyProductResponse.getErrorMessage(), HttpStatus.PRECONDITION_FAILED);
        }
    }
}
