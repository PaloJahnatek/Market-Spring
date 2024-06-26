package com.palo.market.db.service.impl;

import com.palo.market.db.service.api.*;
import com.palo.market.db.service.api.request.BuyProductRequest;
import com.palo.market.db.service.api.response.BuyProductResponse;
import com.palo.market.domain.BoughtProduct;
import com.palo.market.domain.Customer;
import com.palo.market.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    private final ProductService productService;
    private final CustomerService customerService;
    private final CustomerAccountService customerAccountService;
    private final BoughtProductService boughtProductService;

    @Autowired
    public ShoppingServiceImpl(ProductService productService, CustomerService customerService, CustomerAccountService customerAccountService, BoughtProductService boughtProductService) {
        this.productService = productService;
        this.customerService = customerService;
        this.customerAccountService = customerAccountService;
        this.boughtProductService = boughtProductService;
    }

    @Override
    public BuyProductResponse buyProduct(BuyProductRequest request) {
        int productId = request.getProductId();
        int customerId = request.getCustomerId();


        // ak je product null mozme poslat response ktory povie ze je null a na to mame BuyProductResponse
        Product product = productService.get(productId);
        if (product == null) {
            return new BuyProductResponse(false, "Product with id: " + productId + " doesn't exist.");
        }
        Customer customer = customerService.get(customerId);
        if (customer == null) {
            return new BuyProductResponse(false, "Customer with id: " + customerId + " doesn't exist.");
        }

        //kontrola ci produkt ma dostatocny pocet available
        if (product.getAvailable() < request.getQuantity()) {
            return new BuyProductResponse(false, "Not enough products on stock");
        }

        // validacia ci ma customer nejake peniaze .. tu uz vieme ci customer ma nejake peniaze
        Double customerMoney = customerAccountService.getMoney(customerId);
        if (customerMoney == null) {
            return new BuyProductResponse(false, "Customer with id: " + customerId + " doesn't have account");
        } else {
            double totalPriceOfRequest = product.getPrice() * request.getQuantity();
            if (customerMoney >= totalPriceOfRequest) {

                productService.updateAvailableInternal(productId, product.getAvailable() - request.getQuantity());
                customerAccountService.setMoney(customerId, customerMoney - totalPriceOfRequest);
                boughtProductService.add(new BoughtProduct(productId, customerId, request.getQuantity()));
                return new BuyProductResponse(true);

            } else {
                return new BuyProductResponse(false, "Customer with id: " + customerId + " doesn't  have enough money");

            }
        }
    }
}
