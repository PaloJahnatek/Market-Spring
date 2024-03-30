package com.palo.market.db.service.api;

import com.palo.market.db.service.api.request.BuyProductRequest;
import com.palo.market.db.service.api.response.BuyProductResponse;

public interface ShoppingService {
    BuyProductResponse buyProduct(BuyProductRequest request);
}
