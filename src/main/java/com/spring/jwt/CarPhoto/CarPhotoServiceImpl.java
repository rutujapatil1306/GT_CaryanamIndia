package com.spring.jwt.CarPhoto;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.Car.Exception.CarNotFoundException;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.CarPhoto;
import jakarta.mail.Multipart;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CarPhotoServiceImpl implements CarPhotoService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    CarPhotoRepository carPhotoRepository;

    @Autowired
    CarPhotoMapper carPhotoMapper;

    @Override
    public CarPhotoDto uploadCarPhoto(Integer carId, MultipartFile imageFile, DocType type) {
        Car car = carRepository.findById(carId).orElseThrow(()->new CarNotFoundException("Car Not Found for given CarId: " + carId));

        try {
            Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
                    ObjectUtils.asMap("folder", "car_photos"));

            String url = (String) uploadResult.get("secure_url");


            CarPhoto carPhoto = new CarPhoto();
            carPhoto.setCar(car);
            carPhoto.setPhoto_link(url);
            carPhoto.setType(type);


            CarPhoto saved = carPhotoRepository.save(carPhoto);
            return carPhotoMapper.toDto(saved);

        }
        catch(Exception ex)
        {
            throw new RuntimeException("Failed To upload Image ");
        }


    }
}
