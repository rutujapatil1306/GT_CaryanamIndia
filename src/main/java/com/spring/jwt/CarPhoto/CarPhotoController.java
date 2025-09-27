package com.spring.jwt.CarPhoto;
import com.spring.jwt.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
import com.spring.jwt.CarPhoto.DTO.CarPhotoResponseDto;
import com.spring.jwt.dto.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/carPhotos")
public class CarPhotoController {

    @Autowired
    CarPhotoService carPhotoService;


    @GetMapping("/getPhoto")
    public ResponseEntity<CarPhotoResponseDto> uploadCarPhoto(@Valid @RequestParam Integer carId,
                                                      @RequestParam MultipartFile imageFile,
                                                      @RequestParam DocType type)
    {
        CarPhotoDto uploadImage = carPhotoService.uploadCarPhoto(carId, imageFile, type);
        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Added Successfully", "Success", HttpStatus.CREATED, null));
    }

    @GetMapping("/getPhotoById")
    public ResponseEntity<ResponseDto> getCarPhotoById(@RequestParam Integer id)
    {
        CarPhotoDto photo = carPhotoService.getCarPhotoById(id);
        return ResponseEntity.ok(ResponseDto.success("Photo Of Car At PhotoId: " + id, photo));
    }

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
}
