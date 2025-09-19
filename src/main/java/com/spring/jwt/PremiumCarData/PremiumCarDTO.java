package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumCarDTO {

    private Integer premiumCarId;

//    @NotNull(message = "Brand is required")
//    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;

//    @NotBlank(message = "Variant is required")
    private String variant;

//    @NotNull(message = "Price cannot be null")
//    @Positive(message = "Price must be greater than 0")
    private Integer price;

  //  @NotNull(message = "Year is required")
  //  @Min(value = 2000, message = "Year must be >= 2000")
    private Integer year;

  //  @FutureOrPresent(message = "Insurance date must be today or in the future")
    private LocalDate carInsuranceDate;

   // @NotNull(message = "Status is required")
    private Status carstatus;

  //  @NotBlank(message = "City cannot be blank")
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