package com.spring.jwt.dealer.exception;

public class DealerNotFoundException extends RuntimeException {
    public DealerNotFoundException(String message) {
        super(message);
    }
}
