package com.conceptslearning.orderservice.service;

import com.conceptslearning.orderservice.entity.Order;
import com.conceptslearning.orderservice.exception.OrderException;
import com.conceptslearning.orderservice.external.client.PaymentServiceClient;
import com.conceptslearning.orderservice.external.client.ProductServiceClient;
import com.conceptslearning.orderservice.model.*;
import com.conceptslearning.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Autowired
    private RestTemplate restTemplate;

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
        log.info("Calling Payment service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getOrderId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();
        String orderStatus;
        try {
            paymentServiceClient.doPayment(paymentRequest);
            orderStatus = "PLACED";
            log.info("Payment done successfully");
        } catch (Exception e) {
            log.error("Error on Payment ", e);
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return order.getOrderId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderException("NOT_FOUND", "Order not found", 404));
        log.info("Getting Product info");
        ProductResponse productResponse = restTemplate.getForObject("http://product-service/product/" + order.getProductId(), ProductResponse.class);
        log.info("Getting Product done, productResponse:{}", productResponse);

        log.info("Getting Payment info");
        PaymentResponse paymentResponse = restTemplate.getForObject("http://payment-service/payment/order/"+order.getOrderId(), PaymentResponse.class);
        log.info("Getting Payment done, paymentResponse:{}", paymentResponse);
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .productResponse(productResponse)
                .paymentResponse(paymentResponse)
                .build();

    }
}
