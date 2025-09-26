package com.spring.jwt.CarPhoto;
import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
import org.springframework.web.multipart.MultipartFile;

public interface CarPhotoService {

    CarPhotoDto uploadCarPhoto(Integer carId, MultipartFile imageFile, DocType type);

    CarPhotoDto getCarPhotoById(Integer id);

    CarPhotoDto getCarPhotoByCarId(Integer carId);

    CarPhotoDto updateCarPhotoById(Integer id, MultipartFile imageFile, DocType type);

    CarPhotoDto updateCarPhotoByCarId(Integer carId, MultipartFile imageFile, DocType type);

    void deleteCarPhoto(Integer id, boolean hardDelete);

    void deleteCarPhotoByCarId(Integer carId, DocType type, boolean hardDelete);

}
