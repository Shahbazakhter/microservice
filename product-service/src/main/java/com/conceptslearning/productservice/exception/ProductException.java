package com.conceptslearning.productservice.exception;

import lombok.Data;

@Data
public class ProductException extends RuntimeException{
    private String errorCode;

    public ProductException(String errorCode, String message){
        super(message);
        this.errorCode= errorCode;
    }

}
