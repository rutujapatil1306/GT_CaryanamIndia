package com.spring.jwt.premiumcarpendingbooking;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PremiumCarPendingBookingDto {
    private Long premiumCarPendingBookingId;
    private LocalDate date;
    private int price;

    @NotNull(message = "User id must not be null")
    private Integer userId;
    @NotNull(message = "dealer id must not be null")
    private Integer dealerId;
    private String status;

    private Integer premiumCar;

    private int askingPrice;

    public Integer getPremiumCar() {
        return premiumCar;
    }

    public void setPremiumCar(Integer premiumCar) {
        this.premiumCar = premiumCar;
    }

    public PremiumCarPendingBookingDto() {
    }

    public Long getPremiumCarPendingBookingId() {
        return premiumCarPendingBookingId;
    }

    public void setPremiumCarPendingBookingId(Long premiumCarPendingBookingId) {
        this.premiumCarPendingBookingId = premiumCarPendingBookingId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAskingPrice() {
        return askingPrice;
    }

    public void setAskingPrice(int askingPrice) {
        this.askingPrice = askingPrice;
    }
}
