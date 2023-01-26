package com.conceptslearning.orderservice.external.client;

import com.conceptslearning.orderservice.model.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service/payment")
public interface PaymentServiceClient {

    @PostMapping("/doPayment")
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);
}
