package com.palo.market.db.service.impl;

import com.palo.market.db.repository.BoughtProductRepository;
import com.palo.market.db.service.api.BoughtProductService;
import com.palo.market.domain.BoughtProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoughtProductServiceImpl implements BoughtProductService {

    private final BoughtProductRepository boughtProductRepository;

    @Autowired
    public BoughtProductServiceImpl(BoughtProductRepository boughtProductRepository) {
        this.boughtProductRepository = boughtProductRepository;
    }

    @Override
    public void add(BoughtProduct boughtProduct) {
        boughtProductRepository.add(boughtProduct);
    }

    @Override
    public List<BoughtProduct> getAllByCustomerId(int customerId) {
        return boughtProductRepository.getAllByCustomerId(customerId);
    }


}
