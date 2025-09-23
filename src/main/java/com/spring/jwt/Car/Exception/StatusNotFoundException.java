package com.spring.jwt.Car.Exception;

public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(String message) {
        super(message);
    }
}
