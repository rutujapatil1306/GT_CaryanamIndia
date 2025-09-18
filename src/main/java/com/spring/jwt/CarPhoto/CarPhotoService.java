package com.spring.jwt.CarPhoto;


import org.springframework.web.multipart.MultipartFile;

public interface CarPhotoService {

    CarPhotoDto uploadCarPhoto(Integer carId, MultipartFile imageFile, DocType type);


}
