package com.spring.jwt.CarPhoto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/car_photo")
public class CarPhotoController {

    @Autowired
    CarPhotoService carPhotoService;

    @PostMapping("/upload")
    public ResponseEntity<CarPhotoDto> uploadCarPhoto(@RequestParam Integer carId,
                                                      @RequestParam MultipartFile imageFile,
                                                      @RequestParam DocType type)
    {
        CarPhotoDto uploadeImage = carPhotoService.uploadCarPhoto(carId, imageFile, type);
        return ResponseEntity.ok(uploadeImage);
    }



}
