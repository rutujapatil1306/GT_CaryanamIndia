package com.spring.jwt.premiumcarsconfirmbookings;

import com.spring.jwt.entity.TempPendingBookingReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempPendingBookingReqRepository extends JpaRepository<TempPendingBookingReq,Integer> {
    List<TempPendingBookingReq> findByPremiumCarCar_PremiumCarId(Long premiumCarId);

}
