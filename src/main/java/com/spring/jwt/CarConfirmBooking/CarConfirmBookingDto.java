package com.spring.jwt.CarConfirmBooking;

import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.Status;
import com.spring.jwt.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarConfirmBookingDto {

    private Integer id;
    private LocalDate date;
    private int price;
    private int askingPrice;
    private Dealer dealerId;
    private Status status;
    private User userId;
    private Car carCar;




}
