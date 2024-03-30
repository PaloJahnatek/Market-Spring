package com.palo.market.db.service.api;

import com.palo.market.domain.BoughtProduct;

import java.util.List;

public interface BoughtProductService {
    void add(BoughtProduct boughtProduct);

    List<BoughtProduct> getAllByCustomerId(int customerId);

}
