package com.spring.jwt.CarConfirmBooking;

import com.spring.jwt.entity.CarConfirmBooking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CarConfirmBookingMapper {

    public CarConfirmBookingDto toDto(CarConfirmBooking carConfirmBooking){
        if(carConfirmBooking == null){
            return null;
        }
        try{
            CarConfirmBookingDto dto = new CarConfirmBookingDto();
            dto.setId(carConfirmBooking.getId());
            dto.setUserId(carConfirmBooking.getUserId());
            dto.setDealerId(carConfirmBooking.getDealerId());
            dto.setCarCar(carConfirmBooking.getCarCar());
            dto.setDate(carConfirmBooking.getDate());
            dto.setPrice(carConfirmBooking.getPrice());
            dto.setStatus(carConfirmBooking.getStatus());
            dto.setAskingPrice(carConfirmBooking.getAskingPrice());
            return dto;
        }
        catch(Exception ex)
        {
            log.error("Error Converting CarConfirmBooking To Dto: {}", ex.getMessage(),ex);
            throw new RuntimeException("Error converting CarConfirmBooking to DTO", ex);
        }
    }

    public CarConfirmBooking toEntity(CarConfirmBookingDto dto){
        if(dto == null){
            return null;
        }
        try{
            CarConfirmBooking carConfirmBooking = new CarConfirmBooking();
            carConfirmBooking.setId(dto.getId());
            carConfirmBooking.setUserId(dto.getUserId());
            carConfirmBooking.setDealerId(dto.getDealerId());
            carConfirmBooking.setCarCar(dto.getCarCar());
            carConfirmBooking.setDate(dto.getDate());
            carConfirmBooking.setPrice(dto.getPrice());
            carConfirmBooking.setAskingPrice(dto.getAskingPrice());
            return carConfirmBooking;
        }
        catch(Exception ex)
        {
            log.error("Error Converting CarConfirmBookingDto To Entity: {}", ex.getMessage(),ex);
            throw new RuntimeException("Error Converting CarConfirmBookingDto To Entity", ex);
        }
    }
}
