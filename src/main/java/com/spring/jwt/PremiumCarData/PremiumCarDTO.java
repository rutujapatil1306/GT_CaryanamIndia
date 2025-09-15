package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PremiumCarDTO {
    private Integer premiumCarId;

    //  Mandatory fields
    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "MainCarId is required")
    private String mainCarId;

    @NotNull(message = "DealerId cannot be null")
    private Integer dealerId;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Integer price;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Car status is required")
    private Status carStatus;

    //  Optional fields
    private Boolean airbag;
    private Boolean ABS;
    private Boolean buttonStart;
    private Boolean sunroof;
    private Boolean childSafetyLocks;
    private Boolean acFeature;
    private Boolean musicFeature;
    private String area;
    private String variant;
    private Boolean carInsurance;
    private String carInsuranceDate;
    private String carInsuranceType;
    private Boolean pendingApproval;
    private String city;
    private String color;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    private String fuelType;
    private Integer kmDriven;
    private String model;
    private Integer ownerSerial;
    private Boolean powerWindowFeature;
    private Boolean rearParkingCameraFeature;
    private String registration;
    private String transmission;
    private Integer year;
    private LocalDate date;
    private Long carPhotoId;
}
