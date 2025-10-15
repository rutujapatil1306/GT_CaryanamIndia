package com.spring.jwt.pendingbooking;

import com.spring.jwt.entity.PendingBooking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingBookingRepository extends JpaRepository<PendingBooking, Integer> {
    List<PendingBooking> findByDealer_Id(Integer dealerId, Pageable pageable);

    List<PendingBooking> findByUser_Id(Integer userId, Pageable pageable);

    List<PendingBooking> findByCarId(Integer carId);

    long countByDealerId(Integer dealerId);

    long countByUserId(Integer userId);

}

