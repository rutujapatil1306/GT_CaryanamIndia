package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.entity.PremiumCarPendingBooking;
import com.spring.jwt.entity.Status;
import com.spring.jwt.premiumcar.ApiResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface PremiumCarPendingBookingService {
    PremiumCarPendingBooking createBooking(PremiumCarPendingBookingDto dto);
    List<PremiumCarPendingBookingDto> getBookingByDealerId(Integer dealerId,int page,int size);
    List<PremiumCarPendingBookingDto> getBookingByUserId(Integer userId,int page,int size);
    PremiumCarPendingBookingDto getByPendingBookingId(Long bookingId);
    void updateBookingStatus(Long premiumCarPendingBookingId, UserDetails userDetails);
    List<PremiumCarPendingBookingDto> getAllPendingBooking(int page,int size);
}
