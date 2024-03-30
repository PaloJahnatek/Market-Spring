package com.palo.market.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
@Setter
public class Customer {
    @Nullable
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String email;
    @NonNull
    private String address;
    @Nullable
    private Integer age;
    @Nullable
    private String phoneNumber;

    public Customer() {
    }

    public Customer(@NonNull String name, @NonNull String surname, @NonNull String email, @NonNull String address, @Nullable Integer age, @Nullable String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(surname, customer.surname) && Objects.equals(email, customer.email) && Objects.equals(address, customer.address) && Objects.equals(age, customer.age) && Objects.equals(phoneNumber, customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, address, age, phoneNumber);
    }
}
