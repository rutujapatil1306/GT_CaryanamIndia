package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PremiumCarPendingBooking;
import com.spring.jwt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PremiumCarPendingBookingRepository extends JpaRepository<PremiumCarPendingBooking, Long> {

    Page<PremiumCarPendingBooking> findByDealer(Dealer dealer, Pageable pageable);

    // Fetch bookings by user entity
    Page<PremiumCarPendingBooking> findByUser(User user, Pageable pageable);

    Optional<PremiumCarPendingBooking> findById(Long premiumCarPendingBookingId);

    List<PremiumCarPendingBooking> findByPremiumCarCar_PremiumCarId( Long carId);

}
