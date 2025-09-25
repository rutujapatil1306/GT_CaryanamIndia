package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PremiumCarPendingBooking;
import com.spring.jwt.entity.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "premiumCar")
public class PremiumCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premiumCar", nullable = false)
    private Integer premiumCarId;

    @Column(name = "airbag")
    private Boolean airbag;

    @Column(name = "ABS")
    private Boolean ABS;

    @Column(name = "button_start")
    private Boolean buttonStart;

    @Column(name = "sunroof")
    private Boolean sunroof;

    @Column(name = "child_safety_locks")
    private Boolean childSafetyLocks;

    @Column(name = "ac_feature")
    private Boolean acFeature;

    @Column(name = "music_feature")
    private Boolean musicFeature;

    @Column(name = "area", length = 45)
    private String area;

    @NotBlank(message = "Variant is required")
    @Column(name = "variant", length = 45)
    private String variant;
    @NotBlank(message = "Brand is mandatory")
    @Size(max = 45, message = "Brand cannot exceed 45 characters")
    @Pattern(regexp = "^[A-Za-z ]{1,49}$", message = "Brand must contain only letters, spaces")
    @Column(name = "brand", nullable = false, length = 45)
    private String brand;

    @Column(name = "car_insurance")
    private Boolean carInsurance;

    @FutureOrPresent(message = "Insurance date must be today or in the future")
    @Column(name = "car_insurance_date")
    private LocalDate carInsuranceDate;

    @Column(name = "carInsuranceType")
    private String carInsuranceType;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private Status carstatus;

    @Column(name = "pending_approval", nullable = false)
    private boolean pendingApproval;
    @NotBlank(message = "City cannot be blank")
    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "color", length = 45)
    private String color;

    @Column(name = "description", length = 5000)
    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    @Column(name = "fuel_type", length = 45)
    private String fuelType;

    @Column(name = "km_driven", length = 50)
    private Integer kmDriven;

    @Column(name = "model", length = 45)
    private String model;

    @Column(name = "owner_serial")
    private Integer ownerSerial;

    @Column(name = "power_window_feature")
    private Boolean powerWindowFeature;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    @Column(name = "price", length = 45)
    private Integer price;

    @Column(name = "rear_parking_camera_feature")
    private Boolean rearParkingCameraFeature;

    @Column(name = "registration",unique = true, length = 45)
    private String registration;

    @Column(name = "title", length = 250)
    private String title;

    @Column(name = "transmission", length = 45)
    private String transmission;

    @NotNull(message = "Year is required")
    @Min(value = 2000, message = "Year must be >= 2000")
    @Column(name = "year")
    private Integer year;

    @Column(name = "date")
    private LocalDate date;

//    @Column(name = "dealer_id")
//    private Integer dealerId;

    private long carPhotoId;

    @Column(name = "main_car_id",unique = true, nullable = false)
    private String mainCarId;

   @Column(name = "carType", nullable = false)
   private String carType;

    @OneToMany(mappedBy = "premiumCarCar")
    private Set<PremiumCarPendingBooking> pendingBookings = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name ="Dealer_id" ,nullable= false)
    private Dealer dealer;

}
