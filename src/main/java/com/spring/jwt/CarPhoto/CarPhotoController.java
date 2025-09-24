package com.spring.jwt.CarPhoto;

<<<<<<< HEAD
import com.spring.jwt.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
import com.spring.jwt.CarPhoto.DTO.CarPhotoResponseDto;
import com.spring.jwt.dto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
>>>>>>> f6478de2863350de09dee9e4d298974975739906
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
<<<<<<< HEAD
@RequestMapping("/api/cars")
=======
@RequestMapping("/api/carPhotos")
>>>>>>> f6478de2863350de09dee9e4d298974975739906
public class CarPhotoController {

    @Autowired
    CarPhotoService carPhotoService;

    @PostMapping("/photos")
<<<<<<< HEAD
    public ResponseEntity<CarPhotoDto> uploadCarPhoto(@RequestParam Integer carId,
                                                      @RequestParam MultipartFile imageFile,
                                                      @RequestParam DocType type)
    {
        CarPhotoDto uploadeImage = carPhotoService.uploadCarPhoto(carId, imageFile, type);
        return ResponseEntity.ok(uploadeImage);
    }

    @GetMapping("/getPhoto")
=======
    public ResponseEntity<CarPhotoResponseDto> uploadCarPhoto(@Valid @RequestParam Integer carId,
                                                      @RequestParam MultipartFile imageFile,
                                                      @RequestParam DocType type)
    {
        CarPhotoDto uploadImage = carPhotoService.uploadCarPhoto(carId, imageFile, type);
        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Added Successfully", "Success", HttpStatus.CREATED, null));
    }

    @GetMapping("/getPhotoById")
>>>>>>> f6478de2863350de09dee9e4d298974975739906
    public ResponseEntity<ResponseDto> getCarPhotoById(@RequestParam Integer id)
    {
        CarPhotoDto photo = carPhotoService.getCarPhotoById(id);
        return ResponseEntity.ok(ResponseDto.success("Photo Of Car At PhotoId: " + id, photo));
    }

<<<<<<< HEAD


=======
    @GetMapping("/getByCarId")
    public ResponseEntity<ResponseDto> getCarPhotoByCarId(@RequestParam Integer carId)
    {
        CarPhotoDto photoByCarId = carPhotoService.getCarPhotoByCarId(carId);
        return ResponseEntity.ok(ResponseDto.success("Photo of Car with carId: " + carId, photoByCarId));
    }

    @PatchMapping("/updatePhotoById")
    public ResponseEntity<CarPhotoResponseDto> updateCarPhotoById(@Valid @RequestParam Integer id,
                                                                  @RequestParam(required = false) MultipartFile imageFile,
                                                                  @RequestParam(required = false) DocType type)
    {
        CarPhotoDto updateById = carPhotoService.updateCarPhotoById(id, imageFile, type);
        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Updated Successfully by PhotoId: "+ id, "Success", HttpStatus.OK, null));
    }

    @PatchMapping("/updatePhotoByCarId")
    public ResponseEntity<CarPhotoResponseDto> updateCarPhotoByCarId(@Valid @RequestParam Integer carId,
                                                                  @RequestParam(required = false) MultipartFile imageFile,
                                                                  @RequestParam(required = false) DocType type)
    {
        CarPhotoDto updateById = carPhotoService.updateCarPhotoByCarId(carId, imageFile, type);
        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Updated Successfully by CarId: "+ carId, "Success", HttpStatus.OK, null));
    }

    @DeleteMapping("/deleteCarPhotoById")
    public ResponseEntity<CarPhotoResponseDto> deleteCarPhoto(@RequestParam Integer id,
                                                              @RequestParam(defaultValue = "false") boolean hardDelete)
    {
        carPhotoService.deleteCarPhoto(id, hardDelete);
        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Deleted Successfully by PhotoId: "+ id, "Success", HttpStatus.OK, null));
    }

    @DeleteMapping("/deleteCarPhotoByCarId")
    public ResponseEntity<CarPhotoResponseDto> deleteCarPhotoByCarId(@RequestParam Integer carId,
                                                                     @RequestParam DocType type,
                                                                     @RequestParam(defaultValue = "false") boolean hardDelete)
    {
        carPhotoService.deleteCarPhotoByCarId(carId, type, hardDelete);
        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Deleted Successfully by CarId: "+ carId, "Success", HttpStatus.OK, null));
    }
>>>>>>> f6478de2863350de09dee9e4d298974975739906
}
