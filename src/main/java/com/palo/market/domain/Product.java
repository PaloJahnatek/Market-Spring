package com.palo.market.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
public class Product {
    @Nullable
    private Integer id;
    @NonNull
    private int merchantId;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private double price;
    @Nullable
    private Timestamp createdAt;
    @NonNull
    private int available;

    public Product() {
    }

    public Product(int merchantId, @NonNull String name, @NonNull String description, double price, int available) {
        this.merchantId = merchantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.createdAt = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return merchantId == product.merchantId && Double.compare(price, product.price) == 0 &&
                available == product.available && Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                createdAt.getTime() == product.createdAt.getTime();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, merchantId, name, description, price, createdAt, available);
    }
}
