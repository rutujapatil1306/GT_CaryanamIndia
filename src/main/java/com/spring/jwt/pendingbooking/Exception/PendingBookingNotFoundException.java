package com.spring.jwt.pendingbooking.Exception;

public class PendingBookingNotFoundException extends RuntimeException {
    public PendingBookingNotFoundException(String message) {
        super(message);
    }
}
