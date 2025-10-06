package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PremiumCarPendingBooking;
import com.spring.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PremiumCarPendingBookingRepository extends JpaRepository<PremiumCarPendingBooking, Long> {

    List<PremiumCarPendingBooking> findByDealer(Dealer dealer);

    // Fetch bookings by user entity
    List<PremiumCarPendingBooking> findByUser(User user);

    Optional<PremiumCarPendingBooking> findById(long premiumCarPendingBookingId);

}
