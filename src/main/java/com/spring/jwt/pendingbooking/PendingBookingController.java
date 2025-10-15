package com.spring.jwt.pendingbooking;

import com.spring.jwt.entity.PendingBooking;
import com.spring.jwt.entity.Status;
import com.spring.jwt.pendingbooking.DTO.PendingBookingDTO;
import com.spring.jwt.pendingbooking.DTO.PendingBookingResponseDto2;
import com.spring.jwt.premiumcar.ApiResponseDto;
import com.spring.jwt.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pending-bookings")
@RequiredArgsConstructor
public class PendingBookingController {

    private final PendingBookingService pendingbookingservice;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> create(@Valid @RequestBody PendingBookingDTO dto) {
        ApiResponseDto response = pendingbookingservice.create(dto);
        HttpStatus status = response.getCode() == 201 ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PendingBookingDTO> getById(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(pendingbookingservice.getById(id));
    }

    @GetMapping("all")
    public ResponseEntity<List<PendingBookingDTO>> getAll() {

        return ResponseEntity.ok(pendingbookingservice.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> update(@PathVariable Integer id, @RequestBody PendingBookingDTO dto) {
        ApiResponseDto response = pendingbookingservice.update(id, dto);
        HttpStatus status = response.getCode() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pendingbookingservice.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getPendingBookingByDealerId")
    public ResponseEntity<PendingBookingResponseDto2> getPendingBookingByDealerId(@RequestParam Integer dealerId,
                                                                                  @RequestParam int page,
                                                                                  @RequestParam int size)
    {
        List<PendingBookingDTO> bookings = pendingbookingservice.getPendingBookingByDealerId(dealerId, page, size);
        return ResponseEntity.ok(new PendingBookingResponseDto2("List Of Pending Bookings For Dealer with Id: " + dealerId + "for page number " + page, bookings, "Success", HttpStatus.OK, null));
    }


    @GetMapping("/getPendingBookingByUserId")
    public ResponseEntity<PendingBookingResponseDto2> getPendingBookingByUserId(@RequestParam Integer userId,
                                                                                @RequestParam int page,
                                                                                @RequestParam int size)

    {
        List<PendingBookingDTO> bookings = pendingbookingservice.getPendingBookingByUserId(userId, page, size);
        return ResponseEntity.ok(new PendingBookingResponseDto2("List Of Pending Bookings For User with Id: " + userId + " for page number " + page, bookings, "Success", HttpStatus.OK, null));
    }

    @GetMapping("/getPendingBookingById")
    public ResponseEntity<PendingBookingResponseDto2> getPendingBookingById(@RequestParam Integer id)
    {
        return ResponseEntity.ok(new PendingBookingResponseDto2("Pending Booking for Pending id : " + id, pendingbookingservice.getPendingBookingById(id), "Success", HttpStatus.OK, null));
    }

    @PatchMapping("/updatePendingBookingStatusByPendingId")
    public ResponseEntity<PendingBookingResponseDto2> updateBookingStatusByPendingId(@RequestParam Integer id,
                                                                      @RequestParam Status status){
        return ResponseEntity.ok(new PendingBookingResponseDto2("Booking Status Updated Successfully", pendingbookingservice.updateBookingStatusByPendingId(id, status), "Success", HttpStatus.OK,null));
    }

}
