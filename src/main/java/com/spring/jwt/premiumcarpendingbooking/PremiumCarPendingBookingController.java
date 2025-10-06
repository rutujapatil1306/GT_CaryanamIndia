package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.Status;
import com.spring.jwt.entity.User;
import com.spring.jwt.premiumcar.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/premiumcar/pending-booking")
public class PremiumCarPendingBookingController {

    private final PremiumCarPendingBookingService bookingService;

    @PostMapping("/create")
    public ApiResponseDto createBooking(@Valid @RequestBody PremiumCarPendingBookingDto dto){
        return bookingService.createBooking(dto);
    }
    @GetMapping("/dealer")
    public GetApiResponseDto getByDealerId(@RequestParam("dealerId") Integer dealerId) {
        return bookingService.getBookingByDealerId(dealerId);
    }

    @GetMapping("/user")
    public GetApiResponseDto getByUserId(@RequestParam("userId") Integer userId) {
        return bookingService.getBookingByUserId(userId);
    }

    @GetMapping("/pendingId")
    public GetApiResponseDto getByPendingBookingId(@RequestParam Long premiumCarPendingBookingId){
        return  bookingService.getByPendingBookingId(premiumCarPendingBookingId);
    }

    @PatchMapping("/{bookingId}/update-status")
    public ApiResponseDto updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam Integer dealerId,
            @RequestParam Status status) {

        return bookingService.updateBookingStatus(bookingId, dealerId, status);
    }

}

