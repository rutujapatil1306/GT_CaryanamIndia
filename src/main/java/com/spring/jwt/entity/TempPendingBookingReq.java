package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="temp_pending_booking_req")
public class TempPendingBookingReq {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "tempPendingBookingReqId", nullable = false)
        private int tempPendingBookingReqId;

        @Column(name = "date", nullable = false)
        private LocalDate date;

        @Column(name = "price", length = 45)
        private int price;

        @Column(name = "dealerId", length = 45)
        private Integer dealerId;

        @Column(name = "userId", length = 45)
        private Integer userId;

        @Column(name = "car_Id")
        private Integer carId;

        @Enumerated(EnumType.STRING)
        private Status status;

        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @Column (name = "asking_price", nullable = false)
        private int askingPrice;

//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "car_confirm_booking_id")
//        private CarConfirmBooking carConfirmBooking;

        @Column(name = "car_confirm_booking_id")
        private Integer carConfirmBookingId;
}
