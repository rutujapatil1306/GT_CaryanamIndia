package com.spring.jwt.CarConfirmBooking;

import com.spring.jwt.entity.CarConfirmBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarConfirmBookingRepository extends JpaRepository<CarConfirmBooking, Integer> {

}
