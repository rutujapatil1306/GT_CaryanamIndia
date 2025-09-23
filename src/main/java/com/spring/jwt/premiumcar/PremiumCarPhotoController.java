package com.spring.jwt.premiumcar;


import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.premiumcar.PremiumCarPhoto;
import com.spring.jwt.premiumcar.DocType;
import com.spring.jwt.premiumcar.PremiumCarPhotoService;
import com.spring.jwt.entity.Car;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/premiumcars/photos")
@RequiredArgsConstructor
public class PremiumCarPhotoController {

    private final PremiumCarPhotoService photoService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createPhoto(
            @RequestParam int carId,
            @RequestParam String docType,
            @RequestParam MultipartFile file) throws IOException {

        ApiResponseDto response = photoService.createPhoto(carId, docType, file);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponseDto> updatePhoto(
            @RequestParam Long photoId,
            @RequestParam(required = false) String docType,
            @RequestParam(required = false) MultipartFile file) throws IOException {

        ApiResponseDto response = photoService.updatePhoto(photoId, docType, file);
        return ResponseEntity.status(response.getCode()).body(response);
    }
    // Get Photo by ID
    @GetMapping("/{photoId}")
    public ResponseEntity<PremiumCarPhotoDto> getPhotoById(@PathVariable Long photoId) {
        return photoService.getPhotoById(photoId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get Photos by Car ID
    @GetMapping("/byCarId/{carId}")
    public ResponseEntity<List<PremiumCarPhotoDto>> getPhotosByCar(@PathVariable int carId) {
        List<PremiumCarPhotoDto> dtos = photoService.getPhotosByCar(carId);
        return ResponseEntity.ok(dtos);
    }
    // Get Photos by Car ID and docType
    @GetMapping("/byCarAndType")
    public ResponseEntity<List<PremiumCarPhotoDto>> getPhotosByCarAndType(
            @RequestParam int carId,
            @RequestParam String docType) {

        List<PremiumCarPhotoDto> dtos = photoService.getPhotosByCarAndDocType(carId, docType);
        return ResponseEntity.ok(dtos);
    }
    // Delete Photos by photo ID
    @DeleteMapping("/{photoId}")
    public ResponseEntity<String> deletePhotoById(@PathVariable Long photoId) {
        photoService.deletePhotoById(photoId);
        return ResponseEntity.ok("Photo deleted successfully");
    }

    // Delete all photos by carId
    @DeleteMapping("/byCarId/{carId}")
    public ResponseEntity<String> deletePhotosByCar(@PathVariable int carId) {
        photoService.deletePhotosByCar(carId);
        return ResponseEntity.ok("All photos for car deleted successfully");
    }


}

