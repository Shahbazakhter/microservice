package com.conceptslearning.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "date")
    private Instant orderDate;

    @Column(name = "status")
    private String orderStatus;

    @Column(name = "amount")
    private long amount;

}
