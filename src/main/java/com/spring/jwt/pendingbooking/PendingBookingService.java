package com.spring.jwt.pendingbooking;

import com.spring.jwt.premiumcar.ApiResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PendingBookingService {
    ApiResponseDto create(PendingBookingDTO dto);
    PendingBookingDTO getById(Integer id);
    List<PendingBookingDTO> getAll();
    ApiResponseDto update(Integer id, PendingBookingDTO dto);
    void delete(Integer id);
}
