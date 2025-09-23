package com.spring.jwt.Car.DTO;


import com.spring.jwt.Car.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Integer id;
    private Boolean airbag;
    private Boolean abs;
    private Boolean buttonStart;
    private Boolean sunroof;
    private Boolean childSafetyLocks;
    private Boolean acFeature;
    private Boolean musicFeature;
    private String area;
    private String variant;
    private String brand;
    private Boolean carInsurance;
    private LocalDate carInsuranceDate;
    private String carInsuranceType;
    private Status carStatus;
    private Boolean pendingApproval;
    private String city;
    private String color;
    private String description;
    private String fuelType;
    private Integer kmDriven;
    private String model;
    private Integer ownerSerial;
    private Boolean powerWindowFeature;
    private Integer price;
    private Boolean rearParkingCameraFeature;
    private String registration;
    private String title;
    private String transmission;
    private Integer year;
    private LocalDate date;
    private Integer dealerId;
    private Long carPhotoId;
    private String mainCarId;
    private String carType;
    //private long totalNoOfCars;

   // private Set<PendingBooking> pendingBookings = new LinkedHashSet<>(); }

}
