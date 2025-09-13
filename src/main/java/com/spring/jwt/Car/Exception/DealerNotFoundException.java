package com.spring.jwt.Car.Exception;

public class DealerNotFoundException extends RuntimeException {
    public DealerNotFoundException(String message) {
        super(message);
    }
}
