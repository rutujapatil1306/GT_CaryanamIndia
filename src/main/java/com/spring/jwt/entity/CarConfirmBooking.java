package com.spring.jwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_confirm_booking")
public class CarConfirmBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_confirm_booking_id")
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "price", length = 45)
    private int price;

    @ManyToOne
    @JoinColumn(name = "dealer_Id")
    private Dealer dealerId;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User userId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column (name = "asking_price", nullable = false)
    private int askingPrice;

    @ManyToOne
    @JoinColumn(name = "car_car_id")
    @JsonIgnore
    private Car carCar;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pending_booking_id")
//    private PendingBooking pendingBooking;

    @Column(name = "pending_booking_id")
    private Integer pendingBookingId;

}
