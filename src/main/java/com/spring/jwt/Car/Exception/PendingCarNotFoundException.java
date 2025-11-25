package com.spring.jwt.Car.Exception;

public class PendingCarNotFoundException extends RuntimeException
{
    public PendingCarNotFoundException(String message) {
        super(message);
    }
}
