package com.spring.jwt.CarConfirmBooking;

import com.spring.jwt.entity.TempPendingBookingReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempPendingBookingReqRepository extends JpaRepository<TempPendingBookingReq, Integer> {
    List<TempPendingBookingReq> findByCarConfirmBookingId(Integer carConfirmBookingId);
}
