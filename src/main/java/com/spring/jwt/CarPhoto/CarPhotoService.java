package com.spring.jwt.CarPhoto;
import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarPhotoService {

    CarPhotoDto uploadCarPhoto(Integer carId, MultipartFile imageFile, DocType type);

    CarPhotoDto getCarPhotoById(Integer id);


    CarPhotoDto updateCarPhotoByCarId(Integer carId, Integer photoId, MultipartFile imageFile, DocType type);

     void deleteCarPhoto(Integer id);

    List<CarPhotoDto> getCarPhotosByCarId(Integer carId);

    void deleteCarPhotosByCarId(Integer carId);

}
