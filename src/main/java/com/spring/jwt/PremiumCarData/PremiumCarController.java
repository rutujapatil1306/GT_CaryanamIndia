package com.spring.jwt.PremiumCarData;


import com.spring.jwt.PremiumCarBrandData.PremiumBrandResponseDto;
import com.spring.jwt.entity.Status;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/premiums")
public class

PremiumCarController {

    @Autowired
    private PremiumCarService premiumCarService;

    @PostMapping("/add")
    public ResponseEntity<PremiumCarResponseDto> createCar(@Valid @RequestBody PremiumCarDTO dto) {
        PremiumCarDTO premiumCarDTO = premiumCarService.createPremiumCar(dto);


        PremiumCarResponseDto response = new PremiumCarResponseDto(
                "success",
                HttpStatus.OK.value(),
                "Premium Car Added Successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
//
@PatchMapping("/update")
public ResponseEntity<PremiumCarResponseDto> patchPremiumCar(
        @RequestParam Integer id,
         @RequestBody PremiumCarDTO dto) {

    PremiumCarDTO updatedCar = premiumCarService.updatePremiumCar(id, dto);
    PremiumCarResponseDto response = new PremiumCarResponseDto(
            "success",
            HttpStatus.OK.value(),
            "Premium Car updated successfully",
            null,
            LocalDateTime.now()
    );

    return ResponseEntity.ok(response);
}



    @GetMapping("/{id}")
    public ResponseEntity<PremiumResponseDTO> getCarById(@PathVariable Integer id) {
        PremiumCarDTO car = premiumCarService.getPremiumCarById(id);
        return ResponseEntity.ok(new PremiumResponseDTO("success", "Car fetched successfully", car));
    }


   @DeleteMapping("/deletecar")
   public ResponseEntity<PremiumResponseDTO> deleteCar(
        @RequestParam Integer carId,
        @RequestParam(defaultValue = "soft") String type) {

    premiumCarService.deletePremiumCar(carId, type);

    return ResponseEntity.ok(
            new PremiumResponseDTO(
                    "success",
                    "Car with ID " + carId + (type.equalsIgnoreCase("soft") ? " deactivate" : " deleted") + " successfully"
            )
    );
}

    @GetMapping("/dealer/status")
    public ResponseEntity<PremiumResponseDTO> getCarsByDealerAndStatus(
            @RequestParam Integer dealerId,
            @RequestParam(required = false) Status status,
            Pageable pageable) {

        Page<PremiumCarDTO> cars = premiumCarService.getCarsByDealerAndStatus(dealerId, status, pageable);

        return ResponseEntity.ok(
                new PremiumResponseDTO(
                        "success",
                        "Cars fetched successfully for dealerId: " + dealerId +
                                (status != null ? " with status: " + status : ""),
                        cars
                )
        );
    }

    @GetMapping("/car/status")
    public ResponseEntity<Page<PremiumCarDTO>> getCarsByStatus(
            @RequestParam Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            @RequestParam(defaultValue = "premiumCarId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<PremiumCarDTO> cars = premiumCarService.getCarsByStatus(status, page, size, sortBy, sortDir);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/main")
    public ResponseEntity<PremiumResponseDTO> getCarByMainCarId(@RequestParam String mainCarId) {
        PremiumCarDTO car = premiumCarService.getCarByMainCarId(mainCarId);
        return ResponseEntity.ok(new PremiumResponseDTO("success", "Car fetched successfully", car));
    }




}