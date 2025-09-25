package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumCarDTO {
    private Integer premiumCarId;
    private String brand;
    private String variant;
    private Integer price;
    private Integer year;
    private LocalDate carInsuranceDate;
    private Status carstatus;
    private String city;
    private Boolean airbag;
    private Boolean ABS;
    private Boolean buttonStart;
    private Boolean sunroof;
    private Boolean childSafetyLocks;
    private Boolean acFeature;
    private Boolean musicFeature;
    private String area;
    private Boolean carInsurance;
    private String carInsuranceType;
    private boolean pendingApproval;
    private String color;
    private String description;
    private String fuelType;
    private Integer kmDriven;
    private String model;
    private Integer ownerSerial;
    private Boolean powerWindowFeature;
    private Boolean rearParkingCameraFeature;
    private String registration;
    private String title;
    private String transmission;
    private LocalDate date;
    private Integer dealerId;
    private long carPhotoId;
    private String mainCarId;
    private String carType;
}