package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PremiumCarPendingBooking;
import com.spring.jwt.entity.Status;
import com.spring.jwt.entity.User;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
@Entity

public class PremiumCarConfirmBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "dealer_id", nullable = false)
    private Dealer dealer;

    // ✅ Relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ✅ Relationship with Pending Booking
    @OneToOne
    @JoinColumn(name = "pending_booking_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PremiumCarPendingBooking pendingBooking;

    // ✅ Enum stored as string
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    private LocalDate confirmedAt;

    public PremiumCarPendingBooking getPendingBooking() {
        return pendingBooking;
    }

    public void setPendingBooking(PremiumCarPendingBooking pendingBooking) {
        this.pendingBooking = pendingBooking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDate confirmedAt) {
        this.confirmedAt = confirmedAt;
    }
}
