package com.palo.market.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CustomerAccount {
    private int customerId;

    private double money;

    public CustomerAccount() {
    }

    public CustomerAccount(int customerId, double money) {
        this.customerId = customerId;
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAccount that = (CustomerAccount) o;
        return customerId == that.customerId && Double.compare(money, that.money) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, money);
    }
}
