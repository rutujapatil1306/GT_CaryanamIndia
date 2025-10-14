package com.spring.jwt.pendingbooking;

import com.spring.jwt.premiumcar.ApiResponseDto;
import com.spring.jwt.premiumcarpendingbooking.GetApiResponseDto;
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

    @PostMapping("create")
    public ResponseEntity<ApiResponseDto> create(@Valid @RequestBody PendingBookingDTO dto) {
        PendingBookingDTO createBooking = pendingbookingservice.create(dto);
        ApiResponseDto response = new ApiResponseDto(
                "success",
                "pending booking create successfully",
                201,
                "CREATED",
                "null"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetApiResponseDto> getById(@Valid @PathVariable Integer id) {
       PendingBookingDTO dto= pendingbookingservice.getById(id);
        GetApiResponseDto response = new GetApiResponseDto(
                "success",
                "pending booking fetch successfully by id : "+ id,
                dto
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<GetApiResponseDto> getAll() {
        List<PendingBookingDTO> List = pendingbookingservice.getAll();
        GetApiResponseDto response = new GetApiResponseDto(
                "success",
                "All pending booking fetch successfully ",
                List
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDto> update(@PathVariable Integer id, @RequestBody PendingBookingDTO dto) {
        PendingBookingDTO updateBooking = pendingbookingservice.update(id, dto);
        ApiResponseDto response = new ApiResponseDto(
                "success",
                "pending booking update successfully",
                201,
                "UPDATE",
                "null"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Integer id) {
        pendingbookingservice.delete(id);
        ApiResponseDto response = new ApiResponseDto(
                "success",
                "pending booking delete successfully",
                201,
                "DELETE",
                "null"
        );
        return ResponseEntity.ok(response);
    }
}
