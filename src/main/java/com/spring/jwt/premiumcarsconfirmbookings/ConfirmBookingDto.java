package com.spring.jwt.premiumcarsconfirmbookings;

import com.spring.jwt.entity.Status;
import java.time.LocalDate;

public class ConfirmBookingDto {
    private Long confirmId;
    private Long premiumcarpendingbookingid;
    private Integer dealerId;
    private Integer UserId;
    private Status status;
    private LocalDate confirmAt;
    public Long getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(Long confirmId) {
        this.confirmId = confirmId;
    }

    public Long getPremiumcarpendingbookingid() {
        return premiumcarpendingbookingid;
    }

    public void setPremiumcarpendingbookingid(Long premiumcarpendingbookingid) {
        this.premiumcarpendingbookingid = premiumcarpendingbookingid;
    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getConfirmAt() {
        return confirmAt;
    }

    public void setConfirmAt(LocalDate confirmAt) {
        this.confirmAt = confirmAt;
    }
}
