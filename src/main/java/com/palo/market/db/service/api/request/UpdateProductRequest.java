package com.palo.market.db.service.api.request;

import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Getter
public class UpdateProductRequest {
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private double price;
    @NonNull
    private int available;

    public UpdateProductRequest(@NonNull String name, @NonNull String description, double price, int available) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateProductRequest that = (UpdateProductRequest) o;
        return Double.compare(price, that.price) == 0 && available == that.available && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, available);
    }
}
