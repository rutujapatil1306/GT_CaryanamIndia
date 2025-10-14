package com.spring.jwt.dealer;

import com.spring.jwt.dealer.DTO.DealerResponseDto;
import com.spring.jwt.dto.DealerDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dealer")
public class DealerController {

    private final DealerService dealerService;
    // added
    @PatchMapping("/update")
    public ResponseEntity<DealerResponseDto> updateDealer(
            @RequestParam Integer dealerId,
            @RequestBody DealerDTO dealerDTO) {
        return ResponseEntity.ok(dealerService.updateDealer(dealerId, dealerDTO));
    }
    @DeleteMapping
    public ResponseEntity<DealerResponseDto> deleteDealer(@RequestParam Integer dealerId) {
        return ResponseEntity.ok(dealerService.deleteDealer(dealerId));
    }
    @GetMapping("/by-id")
    public ResponseEntity<DealerResponseDto> getDealerById(@RequestParam Integer dealerId) {
        return ResponseEntity.ok(dealerService.getDealerById(dealerId));
    }
    @GetMapping("/all")
    public ResponseEntity<DealerResponseDto> getAllDealers() {
        return ResponseEntity.ok(dealerService.getAllDealers());
    }
    @GetMapping("/by-status")
    public ResponseEntity<DealerResponseDto> getDealersByStatus(@RequestParam DealerStatus status) {
        return ResponseEntity.ok(dealerService.getDealersByStatus(status));
    }
    @GetMapping("/by-user")
    public ResponseEntity<DealerResponseDto> getDealersByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(dealerService.getDealersByUserId(userId));
    }
    @GetMapping("/page")
    public ResponseEntity<DealerResponseDto> getDealersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(dealerService.getDealersWithPagination(page, size, sortBy, sortDir));
    }
    @PatchMapping("/update-status")
    public ResponseEntity<DealerResponseDto> updateDealerStatus(
            @RequestParam Integer dealerId,
            @RequestParam DealerStatus status) {

        return ResponseEntity.ok(dealerService.updateDealerStatus(dealerId, status));
    }

}
