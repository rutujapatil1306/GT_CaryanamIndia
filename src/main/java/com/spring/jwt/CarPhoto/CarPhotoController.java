package com.spring.jwt.CarPhoto;

import com.spring.jwt.dto.ResponseDto;
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
    public ResponseEntity<CarPhotoDto> uploadCarPhoto(@RequestParam Integer carId,
                                                      @RequestParam MultipartFile imageFile,
                                                      @RequestParam DocType type)
    {
        CarPhotoDto uploadeImage = carPhotoService.uploadCarPhoto(carId, imageFile, type);
        return ResponseEntity.ok(uploadeImage);
    }

    @GetMapping("/getPhoto")
    public ResponseEntity<ResponseDto> getCarPhotoById(@RequestParam Integer id)
    {
        CarPhotoDto photo = carPhotoService.getCarPhotoById(id);
        return ResponseEntity.ok(ResponseDto.success("Photo Of Car At PhotoId: " + id, photo));
    }



}
