package com.palo.market.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
public class BoughtProduct {
    private int productId;
    private int customerId;
    private int quantity;
    private Timestamp boughtAt;

    public BoughtProduct() {
    }

    public BoughtProduct(int productId, int customerId, int quantity) {
        this.productId = productId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.boughtAt = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoughtProduct that = (BoughtProduct) o;
        return productId == that.productId && customerId == that.customerId && quantity == that.quantity && Objects.equals(boughtAt, that.boughtAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, customerId, quantity, boughtAt);
    }
}
