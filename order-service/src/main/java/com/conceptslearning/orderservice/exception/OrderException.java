package com.conceptslearning.orderservice.exception;

import lombok.Data;

@Data
public class OrderException extends RuntimeException {
    private String errorCode;
    private int status;

    public OrderException(String errorCode, String message, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

}
