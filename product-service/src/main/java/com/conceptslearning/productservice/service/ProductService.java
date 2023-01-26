package com.conceptslearning.productservice.service;

import com.conceptslearning.productservice.model.ProductRequest;
import com.conceptslearning.productservice.model.ProductResponse;

public interface ProductService {

    long addProduct(ProductRequest request);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
