package com.spring.jwt.premiumcarpendingbooking;

import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.PremiumCarPendingBooking;
import com.spring.jwt.entity.Status;
import com.spring.jwt.entity.User;
import com.spring.jwt.premiumcar.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/premiumcar/pending-booking")
public class PremiumCarPendingBookingController {

    private final PremiumCarPendingBookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createBooking(@Valid @RequestBody PremiumCarPendingBookingDto dto) {
        PremiumCarPendingBooking entity = bookingService.createBooking(dto);

        ApiResponseDto response = new ApiResponseDto(
                "success",
                "Pending booking created successfully",
                201,
                "CREATED",
                "null"
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/dealer")
    public ResponseEntity<GetApiResponseDto> getByDealerId(
            @RequestParam("dealerId") Integer dealerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
               ) {
        var booking = bookingService.getBookingByDealerId(dealerId,page,size);
        GetApiResponseDto response = new GetApiResponseDto(
                "success",
                "Bookings fetched successfully for dealerId: " + dealerId,
                booking
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<GetApiResponseDto> getByUserId(
            @RequestParam("userId") Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size

    ) {
       var booking = bookingService.getBookingByUserId(userId,page,size);
        GetApiResponseDto response = new GetApiResponseDto(
                "success",
                "Bookings fetched successfully for userId: " + userId,
                booking
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/pendingId")
    public ResponseEntity<GetApiResponseDto> getByPendingBookingId(@RequestParam Long premiumCarPendingBookingId) {
        PremiumCarPendingBookingDto booking = bookingService.getByPendingBookingId(premiumCarPendingBookingId);
        GetApiResponseDto response = new GetApiResponseDto(
                "success",
                "Pending booking fetched successfully",
                booking
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/all")
    public ResponseEntity<GetApiResponseDto> getAllPendingBooking(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    )
    {
        List<PremiumCarPendingBookingDto> booking = bookingService.getAllPendingBooking(page,size);
        GetApiResponseDto response = new GetApiResponseDto(
                "success",
                "All pending booking fetch successfully",
                booking
        );
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{bookingId}/update-status")
    @PreAuthorize("hasRole('DEALER')")
    public ResponseEntity<ApiResponseDto> updateBookingStatus(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal UserDetails userDetails) {
        bookingService.updateBookingStatus(bookingId,userDetails );
        ApiResponseDto response = new ApiResponseDto(
                "success",
                "Booking status updated successfully",
                200,
                "OK",
                null
        );
        return ResponseEntity.ok(response);
    }

}

