package com.spring.jwt.dealer;

import com.spring.jwt.dealer.DTO.DealerListResponseDto;
import com.spring.jwt.dealer.DTO.DealerResponseDto;
import com.spring.jwt.dealer.DealerStatus;
import com.spring.jwt.dto.DealerDTO;
import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.entity.Dealer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dealer")
public class DealerController {

    
    private final DealerService dealerService;

    @PatchMapping
    public ResponseEntity<ResponseDto<Dealer>> updateDealer(
            @RequestParam Integer dealerId,
            @RequestBody DealerDTO dealerDTO) {

        Dealer updatedDealer = dealerService.updateDealer(dealerId, dealerDTO);

        return ResponseEntity.ok(
                ResponseDto.success("Dealer updated successfully", updatedDealer)
        );
    }


    @DeleteMapping
    public ResponseEntity<ResponseDto<String>> deleteDealer(@RequestParam Integer dealerId) {
        dealerService.deleteDealer(dealerId);
        return ResponseEntity.ok(ResponseDto.success("Dealer deleted successfully", "Dealer ID: " + dealerId));
    }

    @GetMapping("/by-id")
    public ResponseEntity<DealerResponseDto> getDealerById(@RequestParam Integer dealerId) {
        Dealer dealer = dealerService.getDealerById(dealerId);
        return ResponseEntity.ok(
                DealerResponseDto.success("Dealer fetched successfully", List.of(dealer))
        );
    }

    @GetMapping("/all")
    public ResponseEntity<DealerListResponseDto> getAllDealers() {
        List<Dealer> dealers = dealerService.getAllDealers();

        DealerListResponseDto response = DealerListResponseDto.success(
                "Dealers fetched successfully",
                dealers
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-status")
    public ResponseEntity<DealerResponseDto> getDealersByStatus(@RequestParam DealerStatus status) {
        List<Dealer> dealers = dealerService.getDealersByStatus(status);
        return ResponseEntity.ok(
                DealerResponseDto.success("Dealers fetched successfully", dealers)
        );
    }

    @GetMapping("/by-user")
    public ResponseEntity<DealerResponseDto> getDealersByUserId(@RequestParam Long userId) {
        List<Dealer> dealers = dealerService.getDealersByUserId(userId);
        return ResponseEntity.ok(
                DealerResponseDto.success("Dealers fetched successfully", dealers)
        );
    }


    @GetMapping
    public ResponseEntity<DealerResponseDto> getDealersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<Dealer> dealerPage = dealerService.getDealersWithPagination(page, size, sortBy, sortDir);
        DealerResponseDto responseDto = DealerResponseDto.success(
                "Dealers fetched successfully",
                dealerPage.getContent()
        );
        return ResponseEntity.ok(responseDto);
    }


    @PatchMapping("/update-status")
    public ResponseEntity<DealerResponseDto> updateDealerStatus(
            @RequestParam Integer dealerId,
            @RequestParam DealerStatus status) {

        Dealer updatedDealer = dealerService.updateDealerStatus(dealerId, status);
        return ResponseEntity.ok(
                DealerResponseDto.success("Dealer status updated successfully", List.of(updatedDealer))
        );
    }

}

