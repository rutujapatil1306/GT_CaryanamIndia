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
import jakarta.mail.Multipart;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
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
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car Not Found for given CarId: " + carId));

        boolean exists = carPhotoRepository.existsByCarAndType(car, type);
        if (exists) {
            throw new DuplicatePhotoException("Photo of type " + type + " already exists for this car with carId: " + carId);
        }
        try {
            if (imageFile == null || imageFile.isEmpty()) {
                throw new InvalidFileException("File cannot be empty");
            }
            validateFileFormat(imageFile);

                String hash = DigestUtils.md5DigestAsHex(imageFile.getBytes());

                boolean duplicateHashExists = carPhotoRepository.existsByCarAndHash(car, hash);

                if (duplicateHashExists) {
                    throw new DuplicatePhotoException("This photo already exists for carId: " + carId);
                }
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
                carPhoto.setHash(hash);

                CarPhoto saved = carPhotoRepository.save(carPhoto);
                return carPhotoMapper.toDto(saved);

            }
            catch (IOException e) {
                throw new RuntimeException("Failed To upload Image " + e.getMessage(), e);
            }
    }

    @Override
    public CarPhotoDto getCarPhotoById(Integer id) {
        CarPhoto carPhoto = carPhotoRepository.findById(id)
                .orElseThrow(() -> new PhotoNotFoundException("Car Photo not found for ID: " + id));

        return carPhotoMapper.toDto(carPhoto);
    }


    @Override
        public CarPhotoDto getCarPhotoByCarId (Integer carId){
            CarPhoto photo = carPhotoRepository.findByCarId(carId).orElseThrow(() -> new PhotoNotFoundException("Car Photo not found for CarId: " + carId));

            return carPhotoMapper.toDto(photo);
        }

    @Override
    public CarPhotoDto updateCarPhotoById(Integer id, MultipartFile imageFile, DocType type) {
        CarPhoto carPhoto = carPhotoRepository.findById(id)
                .orElseThrow(() -> new PhotoNotFoundException("Car Photo not found for ID: " + id));

        Car car = carPhoto.getCar();
        if (type != null) {
            boolean typeExists = carPhotoRepository.existsByCarAndTypeAndIdNot(car, type, id);
            if (typeExists) {
                throw new DuplicatePhotoException("Car already has a photo of type " + type);
            }
            carPhoto.setType(type);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            validateFileFormat(imageFile);

            try {

                String hash = DigestUtils.md5DigestAsHex(imageFile.getBytes());

                boolean exists = carPhotoRepository.existsByCarAndHashAndIdNot(carPhoto.getCar(), hash, carPhoto.getId());
                if (exists) {
                    throw new DuplicatePhotoException("This photo has already been uploaded for this car.");
                }

                Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
                        ObjectUtils.asMap("folder", "car_photos"));

                String url = (String) uploadResult.get("secure_url");
                String format = (String) uploadResult.get("format");

                carPhoto.setPhoto_link(url);
                carPhoto.setFileFormat(format);
                carPhoto.setUploadedAt(LocalDateTime.now());
                carPhoto.setHash(hash);

            } catch (IOException e) {
                throw new InvalidFileException("Failed to upload new file");
            }
        }


        CarPhoto saved = carPhotoRepository.save(carPhoto);
        return carPhotoMapper.toDto(saved);
    }

    @Override
        public CarPhotoDto updateCarPhotoByCarId (Integer carId, MultipartFile imageFile, DocType type){
            Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car Not Found At carId : " + carId));
            CarPhoto carPhoto = carPhotoRepository.findByCarId(carId).orElseThrow(() -> new PhotoNotFoundException("Photo not found for CarId: " + carId));

            if (imageFile != null && !imageFile.isEmpty()) {
                validateFileFormat(imageFile);
                try {
                    String hash = DigestUtils.md5DigestAsHex(imageFile.getBytes());

                    // Duplicate check for this car
                    boolean exists = carPhotoRepository.existsByCarAndHashAndIdNot(car, hash, carPhoto.getId());
                    if (exists) {
                        throw new DuplicatePhotoException("This photo has already been uploaded for this car.");
                    }

                    Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
                            ObjectUtils.asMap("folder", "car_photos"));

                    String url = (String) uploadResult.get("secure_url");
                    String fileFormat = (String) uploadResult.get("format");
                    carPhoto.setPhoto_link(url);
                    carPhoto.setFileFormat(fileFormat);
                    carPhoto.setUploadedAt(LocalDateTime.now());
                    carPhoto.setHash(hash);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload new File " + e.getMessage(), e);
                }

            }
            if (type != null) {
                carPhoto.setType(type);
            }

            CarPhoto saved = carPhotoRepository.save(carPhoto);
            return carPhotoMapper.toDto(saved);
        }

        @Override
        public void deleteCarPhoto (Integer id,boolean hardDelete){
            CarPhoto carPhoto = carPhotoRepository.findById(id).orElseThrow(() -> new PhotoNotFoundException("Photo Not Found for id: " + id));

            if (hardDelete) {
                carPhotoRepository.delete(carPhoto);
            } else {
                carPhoto.setStatus(CarPhotoStatus.DELETED);
                carPhotoRepository.save(carPhoto);
            }
        }

        @Override
        public void deleteCarPhotoByCarId (Integer carId, DocType type,boolean hardDelete){

            Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car not Found for id: " + carId));

            CarPhoto carPhoto = carPhotoRepository.findByCarAndType(car, type).orElseThrow(() -> new PhotoNotFoundException("Photo not Found for carId: " + carId));

            if (hardDelete) {
                carPhotoRepository.delete(carPhoto);
            } else {
                carPhoto.setStatus(CarPhotoStatus.DELETED);
                carPhotoRepository.save(carPhoto);
            }
        }

        private void validateFileFormat (MultipartFile imageFile){
            String contentType = imageFile.getContentType();
            if (contentType == null ||
                    !(contentType.equals("image/jpeg") ||
                            contentType.equals("image/jpg") ||
                            contentType.equals("image/png") ||
                            contentType.equals("image/webp"))) {
                throw new InvalidFileException("Only JPEG, JPG, PNG, and WEBP formats are allowed.");
            }
        }
}
