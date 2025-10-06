package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.PremiumCarData.PremiumCar;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PremiumCarPendingBooking;
import com.spring.jwt.entity.User;

public class PremiumCarPendingBookingMapper {
    public static PremiumCarPendingBooking toEntity(PremiumCarPendingBookingDto dto, User user, Dealer dealer,PremiumCar premiumCar){
        PremiumCarPendingBooking entity = new PremiumCarPendingBooking();
        entity.setDate(dto.getDate());
        entity.setPrice(dto.getPrice());
        entity.setDealer(dealer);
        entity.setUser(user);
        entity.setPremiumCarCar(premiumCar);
        entity.setStatus(String.valueOf(entity.getStatus()));
        entity.setAskingPrice(dto.getAskingPrice());
        return entity;
    }

    public static PremiumCarPendingBookingDto toDto(PremiumCarPendingBooking entity){
        PremiumCarPendingBookingDto dto = new PremiumCarPendingBookingDto();
        dto.setPremiumCarPendingBookingId(entity.getPremiumCarPendingBookingId());
        dto.setDate(entity.getDate());
        dto.setPrice(entity.getPrice());
        dto.setDealerId(entity.getDealer() != null ? entity.getDealer().getId() : null);
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setStatus(String.valueOf(entity.getStatus()));
        dto.setAskingPrice(entity.getAskingPrice());
        return dto;
    }
}
