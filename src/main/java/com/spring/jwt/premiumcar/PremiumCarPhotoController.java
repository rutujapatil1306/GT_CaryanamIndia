package com.spring.jwt.premiumcar;


import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.premiumcar.PremiumCarPhoto;
import com.spring.jwt.premiumcar.DocType;
import com.spring.jwt.premiumcar.PremiumCarPhotoService;
import com.spring.jwt.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/premiumcars/photos")
@RequiredArgsConstructor
public class PremiumCarPhotoController {

    private final PremiumCarPhotoService photoService;

    // Create Photo
    @PostMapping("/create")
    public ResponseEntity<PremiumCarPhotoDto> createPhoto(
            @RequestParam int carId,
            @RequestParam String docType,
            @RequestParam MultipartFile file) throws IOException {

        PremiumCarPhotoDto dto = photoService.createPhoto(carId, docType, file);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("{photoId}")
    public ResponseEntity<PremiumCarPhotoDto> updatePhoto(
            @PathVariable Long photoId,
            @RequestParam(required = false) String docType,
            @RequestParam(required = false) MultipartFile file) throws IOException {

        PremiumCarPhotoDto dto = photoService.updatePhoto(photoId, docType, file);
        return ResponseEntity.ok(dto);
    }

    // Get Photo by ID
    @GetMapping("/{photoId}")
    public ResponseEntity<PremiumCarPhotoDto> getPhotoById(@PathVariable Long photoId) {
        return photoService.getPhotoById(photoId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get Photos by Car ID
    @GetMapping("/byCar/{carId}")
    public ResponseEntity<List<PremiumCarPhotoDto>> getPhotosByCar(@PathVariable int carId) {
        List<PremiumCarPhotoDto> dtos = photoService.getPhotosByCar(carId);
        return ResponseEntity.ok(dtos);
    }
}

