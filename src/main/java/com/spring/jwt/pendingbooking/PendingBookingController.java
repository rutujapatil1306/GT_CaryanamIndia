package com.spring.jwt.pendingbooking;

import com.spring.jwt.premiumcar.ApiResponseDto;
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

    private final PendingBookingService service;

    @PostMapping("create")
    public ResponseEntity<ApiResponseDto> create(@Valid @RequestBody PendingBookingDTO dto) {
        ApiResponseDto response = service.create(dto); // service now returns ApiResponseDto
        // Set HTTP status based on success (201) or error (400)
        HttpStatus status = response.getCode() == 201 ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PendingBookingDTO> getById(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("all")
    public ResponseEntity<List<PendingBookingDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> update(@PathVariable Integer id, @RequestBody PendingBookingDTO dto) {
        ApiResponseDto response = service.update(id, dto);
        HttpStatus status = response.getCode() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
