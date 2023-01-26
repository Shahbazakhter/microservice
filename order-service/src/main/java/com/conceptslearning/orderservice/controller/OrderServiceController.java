package com.conceptslearning.orderservice.controller;

import com.conceptslearning.orderservice.model.OrderRequest;
import com.conceptslearning.orderservice.model.OrderResponse;
import com.conceptslearning.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderServiceController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
        long orderID = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderID, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId){

        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
