package com.spring.jwt.pendingbooking.DTO;
import lombok.*;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingBookingDTO {
    private Integer id;
    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Asking price must be 0 or greater")
    private int price;

    @PositiveOrZero(message = "Asking price must be 0 or greater")
    private int askingPrice;
    @NotNull(message = "dealerId is required")
    private Integer dealerId;
    @NotNull(message = "userId is required")
    private Integer userId;
    @NotNull(message = "carId is required")
    private Integer carId;
    private String status;
}