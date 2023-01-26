package com.conceptslearning.paymentservice.controller;

import com.conceptslearning.paymentservice.model.PaymentRequest;
import com.conceptslearning.paymentservice.model.PaymentResponse;
import com.conceptslearning.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentServiceController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/doPayment")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(paymentService.doPayment(paymentRequest), HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable long orderId){
        return new ResponseEntity<>(paymentService.getPaymentByOrderId(orderId), HttpStatus.OK);
    }
}
