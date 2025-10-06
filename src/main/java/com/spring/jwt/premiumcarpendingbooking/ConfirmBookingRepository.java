package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.entity.PremiumCarPendingBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmBookingRepository extends JpaRepository<ConfirmBooking,Long> {
    boolean existsByPendingBooking_PremiumCarPendingBookingId(Long pendingBookingId);
}
