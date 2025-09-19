package com.spring.jwt.CarPhoto;

import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
import com.spring.jwt.dto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cars")
public class CarPhotoController {

    @Autowired
    CarPhotoService carPhotoService;

    @PostMapping("/photos")
    public ResponseEntity<ResponseDto> uploadCarPhoto(@Valid @RequestParam Integer carId,
                                                      @RequestParam MultipartFile imageFile,
                                                      @RequestParam DocType type)
    {
        CarPhotoDto uploadImage = carPhotoService.uploadCarPhoto(carId, imageFile, type);
        return ResponseEntity.ok(ResponseDto.success("Car photo uploaded Successfully", uploadImage));
    }

    @GetMapping("/getPhoto")
    public ResponseEntity<ResponseDto> getCarPhotoById(@RequestParam Integer id)
    {
        CarPhotoDto photo = carPhotoService.getCarPhotoById(id);
        return ResponseEntity.ok(ResponseDto.success("Photo Of Car At PhotoId: " + id, photo));
    }

    @GetMapping("/getPhotoByCarId")
    public ResponseEntity<ResponseDto> getCarPhotoByCarId(@RequestParam Integer carId)
    {
        CarPhotoDto photoByCarId = carPhotoService.getCarPhotoByCarId(carId);
        return ResponseEntity.ok(ResponseDto.success("Photo of Car with carId: " + carId, photoByCarId));
    }

//    @PatchMapping("/update")
//    public ResponseEntity<> updateCarP

}
