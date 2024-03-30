package com.palo.market.db.service.api.request;

import lombok.Getter;

import java.util.Objects;

@Getter
public class BuyProductRequest {
    private int productId;
    private int customerId;
    private int quantity;

    public BuyProductRequest(int productId, int customerId, int quantity) {
        this.productId = productId;
        this.customerId = customerId;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyProductRequest that = (BuyProductRequest) o;
        return productId == that.productId && customerId == that.customerId && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, customerId, quantity);
    }
}
