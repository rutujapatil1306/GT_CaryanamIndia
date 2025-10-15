package com.spring.jwt.CarConfirmBooking;

public class ConfirmBookingNotFoundException extends RuntimeException {
    public ConfirmBookingNotFoundException(String message) {
        super(message);
    }
}
