package com.conceptslearning.orderservice.service;

import com.conceptslearning.orderservice.entity.Order;
import com.conceptslearning.orderservice.external.client.ProductServiceClient;
import com.conceptslearning.orderservice.model.OrderRequest;
import com.conceptslearning.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .quantity(orderRequest.getQuantity())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .build();
        orderRepository.save(order);

        // product service call to reduce quantity
        productServiceClient.reduceQuantity(order.getProductId(), order.getQuantity());

        // payment service call

        return order.getOrderId();
    }
}
