package com.palo.market.db.service.api;

import com.palo.market.db.service.api.request.UpdateProductRequest;
import com.palo.market.domain.Product;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    @Nullable
    Product get(int id);

    @Nullable
    Integer add(Product product); // vracia to vygenerovane id

    void delete(int id);

    void update(int id, UpdateProductRequest request);

    void updateAvailableInternal (int id, int newAvailable);

}
