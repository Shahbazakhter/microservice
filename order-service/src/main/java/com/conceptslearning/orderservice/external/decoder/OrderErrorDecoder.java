package com.conceptslearning.orderservice.external.decoder;

import com.conceptslearning.orderservice.exception.OrderException;
import com.conceptslearning.orderservice.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class OrderErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new OrderException(errorResponse.getErrorCode(), errorResponse.getErrorMessage(), response.status());
        } catch (IOException e) {
            throw new OrderException("SERVER_ERROR", "Internal Server Error", 500);
        }
    }
}
