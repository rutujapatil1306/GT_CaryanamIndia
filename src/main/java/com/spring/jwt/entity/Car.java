package com.spring.jwt.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spring.jwt.Car.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", nullable = false)
    private Integer id;

    @Column(name = "airbag")
    private Boolean airbag;

    @Column(name = "abs")
    private Boolean abs;

    @Column(name = "button_start")
    private Boolean buttonStart;

    @Column(name = "sunroof")
    private Boolean sunroof;

    @Column(name = "child_safety_locks")
    private Boolean childSafetyLocks;

    @Column(name = "ac_feature")
    private Boolean acFeature;

    @NotNull(message = "Music feature is required")
    @Column(name = "music_feature")
    private Boolean musicFeature;

    @NotBlank(message = "Area is Required")
    @Size(max = 45, message = "Area cannot exceed 45 characters")
    @Column(name = "area", length = 45)
    private String area;

    @NotBlank(message = "Variant is required")
    @Size(max = 45, message = "Variant cannot exceed 45 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9\\s-]{0,49}$", message = "Variant must contain only letters, numbers, spaces")
    @Column(name = "variant", length = 45)
    private String variant;

    @NotBlank(message = "Brand is mandatory")
    @Size(max = 45, message = "Brand cannot exceed 45 characters")
    @Pattern(regexp = "^[A-Za-z ]{1,49}$", message = "Brand must contain only letters, spaces")
    @Column(name = "brand", nullable = false, length = 45)
    private String brand;

    @Column(name = "car_insurance")
    private Boolean carInsurance;

    @NotNull(message = "carInsuranceDate is Required")
    @Column(name = "car_insurance_date")
    private LocalDate carInsuranceDate;


    @NotBlank(message = "CarInsuranceType is Required")
    @Size(max = 100, message = "Car insurance type cannot exceed 100 characters")
    @Column(name = "carInsuranceType")
    private String carInsuranceType;


    @Enumerated(EnumType.STRING)
    private Status carStatus;

    @NotNull(message = "Pending approval status is required")
    @Column(name = "pending_approval", nullable = false)
    private Boolean pendingApproval;

    @NotBlank(message = "City is Mandatory")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    @Column(name = "city", length = 50)
    private String city;

    @NotBlank(message = "Color is Required")
    @Size(max = 45, message = "Color cannot exceed 45 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Color must contain only letters")
    @Column(name = "color", length = 45)
    private String color;

    @NotBlank(message = "Description is Required")
    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    @Column(name = "description", length = 5000)
    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;


    @NotBlank(message = "Fuel Type is Mandatory")
    @Size(max = 45, message = "Fuel type cannot exceed 45 characters")
    @Column(name = "fuel_type", length = 45)
    private String fuelType;

    @NotNull(message = "kmDriven can not be empty")
    @Min(value = 0, message = "Kilometers driven cannot be negative")
    @Column(name = "km_driven", length = 50)
    private Integer kmDriven;

    @Size(max = 45, message = "Model cannot exceed 45 characters")
    @Column(name = "model", length = 45)
    private String model;

    @Min(value = 1, message = "Owner serial must be at least 1")
    @Column(name = "owner_serial")
    private Integer ownerSerial;

    @Column(name = "power_window_feature")
    private Boolean powerWindowFeature;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    @Column(name = "price", length = 45)
    private Integer price;

    @Column(name = "rear_parking_camera_feature")
    private Boolean rearParkingCameraFeature;

    @NotBlank(message = "Registration is Required")
    @Size(max = 45, message = "Registration cannot exceed 45 characters")
    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{1,4}$",
            message = "Invalid registration number format"
    )
    @Column(name = "registration", length = 45)
    private String registration;

    @Size(max = 250, message = "Title cannot exceed 250 characters")
    @NotBlank(message = "Title is mandatory")
    @Column(name = "title", length = 250)
    private String title;

    @NotBlank(message = "Transmission is required")
    @Size(max = 45, message = "Transmission cannot exceed 45 characters")
    @Column(name = "transmission", length = 45)
    private String transmission;

    @Min(value = 1900, message = "Year must be after 1900")
    @Max(value = 2100, message = "Year must be a valid year")
    @Column(name = "year")
    private Integer year;


    @NotNull(message = "Date cannot be blank")
    @PastOrPresent(message = "Date cannot be in the future")
    @Column(name = "date")
    private LocalDate date;

//    @Column(name = "dealer_id")
//    private int dealerId;

    private Long carPhotoId;

    @NotBlank(message = "Main Car ID is mandatory")
    @Column(name = "main_car_id", nullable = false)
    private String mainCarId;

    @NotBlank(message = "Car type is mandatory")
    @Column(name = "carType", nullable = false)
    private String carType;

    @OneToMany(mappedBy = "carCar")
    private Set<PendingBooking> pendingBookings = new LinkedHashSet<>();

    @NotNull(message = "Dealer is required")
    @ManyToOne
    @JoinColumn(name = "Dealer_id", nullable = false)
    private Dealer dealer;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarPhoto> carPhoto = new HashSet<>();

}