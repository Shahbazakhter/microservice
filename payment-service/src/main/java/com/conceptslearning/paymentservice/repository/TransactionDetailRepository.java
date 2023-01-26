package com.conceptslearning.paymentservice.repository;

import com.conceptslearning.paymentservice.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {

    TransactionDetail findByOrderId(long orderId);
}
