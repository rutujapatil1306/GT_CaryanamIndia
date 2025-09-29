package com.spring.jwt.pendingbooking;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PendingBooking;
import com.spring.jwt.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PendingBookingMapper {

    // Convert entity → DTO
    public PendingBookingDTO toDTO(PendingBooking pb) {
        if (pb == null) return null;

        return PendingBookingDTO.builder()
                .id(pb.getId())
                .date(pb.getDate())
                .price(pb.getPrice())
                .askingPrice(pb.getAskingPrice())
                .status(pb.getStatus())
                .dealerId(pb.getDealerId() != null ? pb.getDealerId().getId() : null)
                .userId(pb.getUserId() != null ? pb.getUserId().getId() : null)
                .carId(pb.getCarCar() != null ? pb.getCarCar().getId() : null)
                .build();
    }

    // Convert DTO → entity
    public PendingBooking toEntity(PendingBookingDTO dto, Dealer dealer, User user, Car car) {
        if (dto == null) return null;

        PendingBooking pb = new PendingBooking();
        pb.setId(dto.getId());
        pb.setDate(dto.getDate());
        pb.setPrice(dto.getPrice());
        pb.setAskingPrice(dto.getAskingPrice());
        pb.setStatus(dto.getStatus());
        pb.setDealerId(dealer);
        pb.setUserId(user);
        pb.setCarCar(car);

        return pb;
    }
}

