package com.spring.jwt.premiumcar;


import com.spring.jwt.premiumcar.PremiumCarPhoto;
import com.spring.jwt.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PremiumCarPhotoService {
    PremiumCarPhotoDto createPhoto(int carId, String docType, MultipartFile file) throws IOException;
    PremiumCarPhotoDto updatePhoto(Long photoId, String docType, MultipartFile file) throws IOException;
    Optional<PremiumCarPhotoDto> getPhotoById(Long photoId);
    List<PremiumCarPhotoDto> getPhotosByCar(int carId);

    List<PremiumCarPhotoDto> getPhotosByCarAndDocType(int carId, String docType);

    void deletePhotoById(Long photoId);
    void deletePhotosByCar(int carId);
}


