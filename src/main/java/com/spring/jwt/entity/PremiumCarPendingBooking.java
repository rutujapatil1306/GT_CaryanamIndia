package com.spring.jwt.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.jwt.PremiumCarData.PremiumCar;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "PremiumCarPendingBooking")
public class PremiumCarPendingBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premiumCarPendingBookingId", nullable = false)
    private Long premiumCarPendingBookingId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "price", length = 45)
    private int price;

    @ManyToOne
    @JoinColumn(name = "dealer_id",nullable = false)
    private Dealer dealer;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "status")
    private String status;

    @Column (name = "asking_price", nullable = false)
    private int askingPrice;

    @ManyToOne
    @JoinColumn(name = "premium_car_car_id")
    @JsonIgnore
    private PremiumCar premiumCarCar;


    public Long getPremiumCarPendingBookingId() {
        return premiumCarPendingBookingId;
    }

    public void setPremiumCarPendingBookingId(Long premiumCarPendingBookingId) {
        this.premiumCarPendingBookingId = premiumCarPendingBookingId;
    }

}
