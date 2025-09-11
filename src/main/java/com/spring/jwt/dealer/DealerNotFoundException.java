package com.spring.jwt.dealer;

public class DealerNotFoundException extends RuntimeException {
    public DealerNotFoundException(String message) {
        super(message);
    }
}
