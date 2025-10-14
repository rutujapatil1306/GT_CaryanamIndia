package com.spring.jwt.premiumcarsconfirmbookings;

public interface PremiumCarBookingService {

    ConfirmBookingDto confirmBooking(Long pendingBookingId);

    String deleteConfirmedBooking(Long confirmBookingId);
}
