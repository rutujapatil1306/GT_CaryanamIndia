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

    // Create photo
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createPhoto(
            @RequestParam int premiumCarId,     // ✅ changed
            @RequestParam String docType,
            @RequestParam MultipartFile file) throws IOException {

        ApiResponseDto response = photoService.createPhoto(premiumCarId, docType, file);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    // Update photo
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

    // Get Photos by PremiumCar ID
    @GetMapping("/byCarId/{premiumCarId}")     // ✅ changed
    public ResponseEntity<List<PremiumCarPhotoDto>> getPhotosByCar(@PathVariable int premiumCarId) {
        List<PremiumCarPhotoDto> dtos = photoService.getPhotosByCar(premiumCarId);
        return ResponseEntity.ok(dtos);
    }

    // Get Photos by PremiumCar ID and docType
    @GetMapping("/byCarAndType")
    public ResponseEntity<List<PremiumCarPhotoDto>> getPhotosByCarAndType(
            @RequestParam int premiumCarId,     // ✅ changed
            @RequestParam String docType) {

        List<PremiumCarPhotoDto> dtos = photoService.getPhotosByCarAndDocType(premiumCarId, docType);
        return ResponseEntity.ok(dtos);
    }

    // Delete Photo by ID
    @DeleteMapping("/{photoId}")
    public ResponseEntity<String> deletePhotoById(@PathVariable Long photoId) {
        photoService.deletePhotoById(photoId);
        return ResponseEntity.ok("Photo deleted successfully");
    }

    // Delete all Photos by PremiumCar ID
    @DeleteMapping("/byCarId/{premiumCarId}")   // ✅ changed
    public ResponseEntity<String> deletePhotosByCar(@PathVariable int premiumCarId) {
        photoService.deletePhotosByCar(premiumCarId);
        return ResponseEntity.ok("All photos for premium car deleted successfully");
    }
}


