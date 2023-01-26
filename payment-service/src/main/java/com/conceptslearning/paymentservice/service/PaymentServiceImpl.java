package com.conceptslearning.paymentservice.service;

import com.conceptslearning.paymentservice.entity.TransactionDetail;
import com.conceptslearning.paymentservice.model.PaymentMode;
import com.conceptslearning.paymentservice.model.PaymentRequest;
import com.conceptslearning.paymentservice.model.PaymentResponse;
import com.conceptslearning.paymentservice.repository.TransactionDetailRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionDetailRepository repository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment:{}", paymentRequest);
        TransactionDetail transactionDetail = TransactionDetail.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        repository.save(transactionDetail);
        log.info("Transaction complete with id :{}", transactionDetail.getId());
        return transactionDetail.getId();
    }

    @Override
    public PaymentResponse getPaymentByOrderId(long orderId) {
        log.info("Getting payment for orderId:{}", orderId);
        TransactionDetail transactionDetail = repository.findByOrderId(orderId);

        if(transactionDetail==null){
            throw new RuntimeException("Transaction Not found for orderID:"+orderId);
        }
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionDetail.getId())
                .paymentDate(transactionDetail.getPaymentDate())
                .status(transactionDetail.getPaymentStatus())
                .paymentMode(PaymentMode.valueOf(transactionDetail.getPaymentMode()))
                .orderId(transactionDetail.getOrderId())
                .build();

        return paymentResponse;
    }
}
