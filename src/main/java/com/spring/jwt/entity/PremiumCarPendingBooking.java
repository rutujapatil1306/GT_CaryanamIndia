package com.spring.jwt.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.jwt.PremiumCarData.PremiumCar;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "date is required")
    private LocalDate date;

    @Column(name = "price", length = 45)
    @NotNull(message = "Mobile number is required")
    private int price;

    @ManyToOne
    @JoinColumn(name = "dealer_id",nullable = true)
    private Dealer dealer;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
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
    @NotBlank(message = "Status is required")
    private String status;

    @Column (name = "asking_price", nullable = false)
    @NotNull(message = "Asking price  is required")
    private int askingPrice;

    @ManyToOne
    @JoinColumn(name = "premium_car_car_id")
    @JsonIgnore
    @NotNull(message = "premium car id  is required")
    private PremiumCar premiumCarCar;


    public Long getPremiumCarPendingBookingId() {
        return premiumCarPendingBookingId;
    }

    public void setPremiumCarPendingBookingId(Long premiumCarPendingBookingId) {
        this.premiumCarPendingBookingId = premiumCarPendingBookingId;
    }


    public PremiumCar getPremiumCarCar() {
        return premiumCarCar;
    }

    public void setPremiumCarCar(PremiumCar premiumCarCar) {
        this.premiumCarCar = premiumCarCar;
    }
}
