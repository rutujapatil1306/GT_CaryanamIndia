package com.spring.jwt.CarPhoto;


import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
import org.springframework.web.multipart.MultipartFile;

public interface CarPhotoService {

    CarPhotoDto uploadCarPhoto(Integer carId, MultipartFile imageFile, DocType type);

    CarPhotoDto getCarPhotoById(Integer id);


    CarPhotoDto getCarPhotoByCarId(Integer carId);
}
