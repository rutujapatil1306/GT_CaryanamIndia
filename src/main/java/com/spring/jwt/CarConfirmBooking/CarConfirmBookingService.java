package com.spring.jwt.CarConfirmBooking;

import com.spring.jwt.entity.CarConfirmBooking;
import com.spring.jwt.service.impl.UserServiceImpl;

public interface CarConfirmBookingService {

    CarConfirmBookingDto confirmBooking(Integer pendingBookingId);//, UserServiceImpl userDetails);

    void deleteConfirmBooking(Integer confirmBookingId);
}
