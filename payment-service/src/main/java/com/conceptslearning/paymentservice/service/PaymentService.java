package com.conceptslearning.paymentservice.service;

import com.conceptslearning.paymentservice.model.PaymentRequest;
import com.conceptslearning.paymentservice.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentByOrderId(long orderId);
}
