package com.spring.jwt.CarPhoto;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.Car.Exception.CarNotFoundException;
import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
import com.spring.jwt.CarPhoto.Exception.DuplicatePhotoException;
import com.spring.jwt.CarPhoto.Exception.InvalidFileException;
import com.spring.jwt.CarPhoto.Exception.PhotoNotFoundException;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.CarPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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

        boolean exists = carPhotoRepository.existsByCarAndType(car, type);
        if (exists) {
            throw new DuplicatePhotoException("Photo of type " + type + " already exists for this car with carId: " + carId);
        }
        try {
            Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
                    ObjectUtils.asMap("folder", "car_photos"));

            String url = (String) uploadResult.get("secure_url");

            String contentType = imageFile.getContentType();
            String fileFormat = null;
            if (contentType != null && contentType.startsWith("image/")) {
                fileFormat = contentType.substring(contentType.lastIndexOf("/") + 1);
            } else {
                throw new InvalidFileException("Invalid file format. Only images are allowed.");
            }

            CarPhoto carPhoto = new CarPhoto();
            carPhoto.setCar(car);
            carPhoto.setPhoto_link(url);
            carPhoto.setType(type);
            carPhoto.setFileFormat(fileFormat);
            carPhoto.setUploadedAt(LocalDateTime.now());


            CarPhoto saved = carPhotoRepository.save(carPhoto);
            return carPhotoMapper.toDto(saved);

        }
        catch(IOException e)
        {
            throw new RuntimeException("Failed To upload Image "+ e.getMessage(),e);
        }


    }

    @Override
    public CarPhotoDto getCarPhotoById(Integer id) {
        CarPhoto carPhoto = carPhotoRepository.findById(id)
                .orElseThrow(() -> new PhotoNotFoundException("Car Photo not found for ID: " + id));

        return carPhotoMapper.toDto(carPhoto);
    }

    @Override
    public CarPhotoDto getCarPhotoByCarId(Integer carId) {
        CarPhoto photo = carPhotoRepository.findByCarId(carId).orElseThrow(()-> new PhotoNotFoundException("Car Photo not found for CarId: " + carId));

        return carPhotoMapper.toDto(photo);
    }
}
