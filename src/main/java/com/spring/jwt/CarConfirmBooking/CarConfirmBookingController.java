package com.spring.jwt.CarConfirmBooking;

import com.spring.jwt.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carConfirmBooking")
public class CarConfirmBookingController {

    @Autowired
    CarConfirmBookingService carConfirmBookingService;

//    @PostMapping("/createConfirmBooking")
//    @PreAuthorize("hasRole('DEALER')")
//    public ResponseEntity<com.spring.jwt.utils.ResponseDto> confirmBooking(@RequestParam Integer pendingBookingId,
//                                                                           Authentication authentication)
//    {
//        UserServiceImpl userDetails = (UserServiceImpl) authentication.getPrincipal();
//        carConfirmBookingService.confirmBooking(pendingBookingId);//, userDetails);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new com.spring.jwt.utils.ResponseDto("success", "Booking confirmed Successfully"));
//
//    }

    @PostMapping("/createConfirmBooking")
    public ResponseEntity<com.spring.jwt.utils.ResponseDto> confirmBooking(@RequestParam Integer pendingBookingId)
    {

        carConfirmBookingService.confirmBooking(pendingBookingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new com.spring.jwt.utils.ResponseDto("success", "Booking confirmed Successfully"));

    }

    @DeleteMapping("/deleteConfirmBooking")
    public ResponseEntity<?> deleteConfirmBooking(@RequestParam Integer carConfirmBookingId){
    carConfirmBookingService.deleteConfirmBooking(carConfirmBookingId);
    return  ResponseEntity.ok("Confirm Booking Deleted Successfully");
    }

}
