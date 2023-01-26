package com.conceptslearning.orderservice.service;

import com.conceptslearning.orderservice.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
