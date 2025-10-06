package com.spring.jwt.pendingbooking;
import com.spring.jwt.entity.*;
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
                .dealerId(pendingbooking.getDealerId() != null ? pendingbooking.getDealerId().getId() : null)
                .userId(pendingbooking.getUserId() != null ? pendingbooking.getUserId().getId() : null)
                .carId(pendingbooking.getCarCar() != null ? pendingbooking.getCarCar().getId() : null)
                .build();
    }
    public PendingBooking toEntity(PendingBookingDTO dto, Dealer dealer, User user, Car car) {
        PendingBooking pendingbooking = new PendingBooking();
        pendingbooking.setId(dto.getId());
        pendingbooking.setDate(dto.getDate());
        pendingbooking.setPrice(dto.getPrice());
        pendingbooking.setAskingPrice(dto.getAskingPrice());
        pendingbooking.setStatus(Status.fromString(dto.getStatus()));
        pendingbooking.setDealerId(dealer);
        pendingbooking.setUserId(user);
        pendingbooking.setCarCar(car);
        return pendingbooking;
    }
}

