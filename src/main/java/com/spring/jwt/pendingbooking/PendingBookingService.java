package com.spring.jwt.pendingbooking;

import com.spring.jwt.premiumcar.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PendingBookingService {
    PendingBookingDTO create(PendingBookingDTO dto);
    PendingBookingDTO getById(Integer id);
    List<PendingBookingDTO> getAll();
    PendingBookingDTO update(Integer id, PendingBookingDTO dto);
    void delete(Integer id);
}
