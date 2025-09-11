package com.spring.jwt.dealer;

public class InvalidDealerDataException extends RuntimeException {
    public InvalidDealerDataException(String message) {
        super(message);
    }
}
