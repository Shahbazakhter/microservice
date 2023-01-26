package com.conceptslearning.orderservice.service;

import com.conceptslearning.orderservice.model.OrderRequest;
import com.conceptslearning.orderservice.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
