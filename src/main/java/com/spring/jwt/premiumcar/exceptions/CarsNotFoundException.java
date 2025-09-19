package com.spring.jwt.premiumcar.exceptions;
public class CarsNotFoundException extends RuntimeException {
    public CarsNotFoundException(String message) {
        super(message);
    }
}
