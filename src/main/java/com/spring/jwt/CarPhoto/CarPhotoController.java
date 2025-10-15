package com.spring.jwt.CarPhoto;
import com.spring.jwt.CarPhoto.DTO.CarPhotoResponseDto2;
import com.spring.jwt.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
import com.spring.jwt.CarPhoto.DTO.CarPhotoResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/carPhotos")
public class CarPhotoController {

    @Autowired
    CarPhotoService carPhotoService;

    @PostMapping("/addCarPhoto")
    public ResponseEntity<CarPhotoResponseDto2> uploadCarPhotos(@Valid @RequestParam Integer carId,
                                                               @RequestParam List<MultipartFile> files,
                                                               @RequestParam DocType type){
        List<CarPhotoDto> uploadImages = carPhotoService.uploadCarPhotos(carId, files, type);
        if(type == DocType.COVER) {
            CarPhotoResponseDto2 response = new CarPhotoResponseDto2("Cover Photo for Car with id " + carId + " Added Successfully", uploadImages, "Success", HttpStatus.CREATED, null);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else  {
            CarPhotoResponseDto2 response = new CarPhotoResponseDto2("Additional Photos for Car with id " + carId + " Added Successfully",uploadImages, "Success", HttpStatus.CREATED, null);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }
    }
   
    @DeleteMapping("/deleteCarPhotoById")
    public ResponseEntity<CarPhotoResponseDto> deleteCarPhoto(@RequestParam Integer id)
    {
        carPhotoService.deleteCarPhoto(id);
        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Deleted Successfully by PhotoId: "+ id, "Success", HttpStatus.OK, null));
    }
    @GetMapping("/getPhotoById")
    public ResponseEntity<ResponseDto> getCarPhotoById(@RequestParam Integer id)
    {
        CarPhotoDto photo = carPhotoService.getCarPhotoById(id);
        return ResponseEntity.ok(ResponseDto.success("Photo Of Car At PhotoId: " + id, photo));
    }
    @GetMapping("/getByCarId")
    public ResponseEntity<CarPhotoResponseDto2> getCarPhotoByCarId(@RequestParam Integer carId)
    {
        List<CarPhotoDto> photos = carPhotoService.getCarPhotosByCarId(carId);
        return ResponseEntity.ok(new CarPhotoResponseDto2("Photos of Car with carId: " + carId, photos, "Success", HttpStatus.OK, null));
    }
    @DeleteMapping("/deleteCarPhotosByCarId")
    public ResponseEntity<CarPhotoResponseDto> deleteCarPhotosByCarId(@RequestParam Integer carId)
    {
        carPhotoService.deleteCarPhotosByCarId(carId);
        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Deleted Successfully by CarId: "+ carId, "Success", HttpStatus.OK, null));
    }
    @PatchMapping("/updatePhotoByCarId")
    public ResponseEntity<CarPhotoResponseDto> updateCarPhotoByCarId(@Valid @RequestParam Integer carId,
                                                                  @RequestParam Integer photoId,
                                                                  @RequestParam(required = false) MultipartFile imageFile,
                                                                  @RequestParam(required = false) DocType type)
    {
        CarPhotoDto updateById = carPhotoService.updateCarPhotoByCarId(carId, photoId, imageFile, type);
        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Updated Successfully by CarId: "+ carId, "Success", HttpStatus.OK, null));
    }
}


//
//
//
//    @PatchMapping("/updatePhotoById")
//    public ResponseEntity<CarPhotoResponseDto> updateCarPhotoById(@Valid @RequestParam Integer id,
//                                                                  @RequestParam(required = false) MultipartFile imageFile,
//                                                                  @RequestParam(required = false) DocType type)
//    {
//        CarPhotoDto updateById = carPhotoService.updateCarPhotoById(id, imageFile, type);
//        return ResponseEntity.ok(new CarPhotoResponseDto("CarPhoto Updated Successfully by PhotoId: "+ id, "Success", HttpStatus.OK, null));
//    }
//
//
//

//

//}
