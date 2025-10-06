package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.entity.Status;
import com.spring.jwt.premiumcar.ApiResponseDto;

public interface PremiumCarPendingBookingService {
    ApiResponseDto createBooking(PremiumCarPendingBookingDto dto);
    GetApiResponseDto getBookingByDealerId(Integer dealerId);

    GetApiResponseDto getBookingByUserId(Integer userId);
    GetApiResponseDto getByPendingBookingId(Long premiumCarPendingBookingId);

    ApiResponseDto updateBookingStatus(Long bookingId, Integer dealerId, Status status);
}
