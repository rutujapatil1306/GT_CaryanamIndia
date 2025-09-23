package com.spring.jwt.Car.Exception;

public class CarAlreadyExistsException extends RuntimeException {
    public CarAlreadyExistsException(String message) {
        super(message);
    }
}
