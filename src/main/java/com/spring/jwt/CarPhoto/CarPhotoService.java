package com.spring.jwt.CarPhoto;


<<<<<<< HEAD
=======
import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
>>>>>>> f6478de2863350de09dee9e4d298974975739906
import org.springframework.web.multipart.MultipartFile;

public interface CarPhotoService {

    CarPhotoDto uploadCarPhoto(Integer carId, MultipartFile imageFile, DocType type);

    CarPhotoDto getCarPhotoById(Integer id);


<<<<<<< HEAD
=======
    CarPhotoDto getCarPhotoByCarId(Integer carId);

    CarPhotoDto updateCarPhotoById(Integer id, MultipartFile imageFile, DocType type);

    CarPhotoDto updateCarPhotoByCarId(Integer carId, MultipartFile imageFile, DocType type);

    void deleteCarPhoto(Integer id, boolean hardDelete);

    void deleteCarPhotoByCarId(Integer carId, DocType type, boolean hardDelete);
>>>>>>> f6478de2863350de09dee9e4d298974975739906
}
