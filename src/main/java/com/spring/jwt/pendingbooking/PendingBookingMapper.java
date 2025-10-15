package com.spring.jwt.pendingbooking;
import com.spring.jwt.entity.*;
import com.spring.jwt.pendingbooking.DTO.PendingBookingDTO;
import org.springframework.stereotype.Component;
@Component
public class PendingBookingMapper {
    public PendingBookingDTO toDTO(PendingBooking pendingbooking) {
        return PendingBookingDTO.builder()
                .id(pendingbooking.getId())
                .date(pendingbooking.getDate())
                .price(pendingbooking.getPrice())
                .askingPrice(pendingbooking.getAskingPrice())
                .status(String.valueOf(pendingbooking.getStatus()))
                .dealerId(pendingbooking.getDealer() != null ? pendingbooking.getDealer().getId() : null)
                .userId(pendingbooking.getUser() != null ? pendingbooking.getUser().getId() : null)
                .carId(pendingbooking.getCar() != null ? pendingbooking.getCar().getId() : null)
                .build();
    }
    public PendingBooking toEntity(PendingBookingDTO dto, Dealer dealer, User user, Car car) {
        PendingBooking pendingbooking = new PendingBooking();
        pendingbooking.setId(dto.getId());
        pendingbooking.setDate(dto.getDate());
        pendingbooking.setPrice(dto.getPrice());
        pendingbooking.setAskingPrice(dto.getAskingPrice());
        pendingbooking.setStatus(Status.fromString(dto.getStatus()));
        pendingbooking.setDealer(dealer);
        pendingbooking.setUser(user);
        pendingbooking.setCar(car);
        return pendingbooking;
    }
}

