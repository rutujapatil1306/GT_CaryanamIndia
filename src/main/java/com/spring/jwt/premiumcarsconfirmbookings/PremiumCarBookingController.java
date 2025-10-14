package com.spring.jwt.premiumcarsconfirmbookings;

import com.spring.jwt.premiumcar.ApiResponseDto;
import com.spring.jwt.premiumcarpendingbooking.GetApiResponseDto;
import com.spring.jwt.premiumcarsconfirmbookings.ConfirmBookingDto;
import com.spring.jwt.premiumcarsconfirmbookings.PremiumCarBookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@AllArgsConstructor
@RequestMapping("/api/bookings")
public class PremiumCarBookingController {
    private final PremiumCarBookingService bookingService;
    @PostMapping("/confirm/{pendingBookingId}")
    public ResponseEntity<ApiResponseDto> confirmBooking(@RequestParam Long pendingBookingId) {
        bookingService.confirmBooking(pendingBookingId);
        ApiResponseDto response = new ApiResponseDto(
                "success",
                "Premium Car Pending Booking create successfully",
                200,
                "CREATE",
                null
        );
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{confirmBookingId}")
    public ResponseEntity<GetApiResponseDto> deleteConfirmedBooking(@PathVariable Long confirmBookingId) {
        String message = bookingService.deleteConfirmedBooking(confirmBookingId);
        return ResponseEntity.ok(new GetApiResponseDto("success",
                "Bookings delete successfully : ",
                message));
    }
}

