package com.spring.jwt.PremiumCarData;

import com.spring.jwt.entity.Status;
import com.spring.jwt.utils.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/premiumCar")
public class PremiumController {
    @Autowired
    private PremiumService premiumService;


    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addPremiumCar(@RequestBody PremiumCarDTO dto) {
        PremiumCarDTO savedCar = premiumService.save(dto);
        return ResponseEntity.ok(new ResponseDto("succes","Premium Car added"));
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseDto> updatePremiumCar(
            @PathVariable("id") Integer id,
            @RequestBody PremiumCarDTO premiumCarDTO) {
        PremiumCarDTO updatedCar = premiumService.update(premiumCarDTO, id);

        return ResponseEntity.ok(new ResponseDto("succes","Premium Car updated"));
    }

@GetMapping("/{carId}")
public ResponseEntity<PremiumDTO> getCarById(@PathVariable Integer carId) {
    PremiumCarDTO car = premiumService.findById(carId);
    return ResponseEntity.ok(PremiumDTO.success("data Found Successfully",car));
    }



    @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDto> deletePremiumCarById(@RequestParam Integer id) {
        PremiumCarDTO carId = premiumService.findById(id);
        return ResponseEntity.ok(new ResponseDto("success","Premium Car deleted"));
    }

//    DELETE http://localhost:8080/premiumcars/1?type=soft
    @DeleteMapping("/Premium/{carId}")
    public ResponseEntity<ResponseDto> deletePremiumCar(
            @PathVariable Integer carId,
            @RequestParam(defaultValue = "soft") String type) {

        if ("soft".equalsIgnoreCase(type)) {
            PremiumCarDTO updatedCar = premiumService.softDelete(carId);
            return ResponseEntity.ok(new ResponseDto("Success","Car soft deleted successfully"));
        } else {
            throw new IllegalArgumentException("Only soft delete is supported right now");
        }
    }
    @DeleteMapping("/{carId}")
    public ResponseEntity<ResponseDto> hardDeletePremiumCar(
            @PathVariable Integer carId,
            @RequestParam(defaultValue = "hard") String type) {

        if ("hard".equalsIgnoreCase(type)) {
            PremiumCarDTO deletedCar = premiumService.hardDelete(carId);
            return ResponseEntity.ok(new ResponseDto("Success", " hard car deleted successfully"));
        } else {
            throw new IllegalArgumentException("Invalid delete type. Use 'hard'");
        }

    }


}

