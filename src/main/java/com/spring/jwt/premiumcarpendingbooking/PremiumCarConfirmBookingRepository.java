package com.spring.jwt.premiumcarpendingbooking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PremiumCarConfirmBookingRepository extends JpaRepository<PremiumCarConfirmBooking, Long> {

    boolean existsByPendingBooking_PremiumCarPendingBookingId(Long pendingBookingId);


}
