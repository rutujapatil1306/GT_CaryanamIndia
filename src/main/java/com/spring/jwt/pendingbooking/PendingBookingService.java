package com.spring.jwt.pendingbooking;

import com.spring.jwt.entity.Status;
import com.spring.jwt.pendingbooking.DTO.PendingBookingDTO;
import com.spring.jwt.premiumcar.ApiResponseDto;

import java.util.List;

public interface PendingBookingService {
    ApiResponseDto create(PendingBookingDTO dto);
    PendingBookingDTO getById(Integer id);
    List<PendingBookingDTO> getAll();
    ApiResponseDto update(Integer id, PendingBookingDTO dto);
    void delete(Integer id);

    List<PendingBookingDTO> getPendingBookingByDealerId(Integer dealerId, int page, int size);
    List<PendingBookingDTO> getPendingBookingByUserId(Integer userId, int page, int size);
    PendingBookingDTO getPendingBookingById(Integer id);
    PendingBookingDTO updateBookingStatusByPendingId(Integer id, Status status);
}
