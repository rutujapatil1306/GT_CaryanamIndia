package com.spring.jwt.premiumcar;
import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.entity.Car;
import com.spring.jwt.premiumcar.exceptions.CarsNotFoundException;
import com.spring.jwt.premiumcar.exceptions.DuplicatePhotosException;
import com.spring.jwt.premiumcar.exceptions.InvalidCarFileExceptions;
import com.spring.jwt.premiumcar.exceptions.PhotosNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PremiumCarPhotoServiceImpl implements PremiumCarPhotoService {

    private final PremiumCarPhotoRepository repository;
    private final CarRepository carRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public ApiResponseDto createPhoto(int carId, String docType, MultipartFile file) throws IOException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarsNotFoundException("Car not found with id: " + carId));

        if (docType == null || docType.isBlank()) throw new InvalidCarFileExceptions("docType is required");
        DocType validatedDocType;
        try {
            validatedDocType = DocType.valueOf(docType);
        } catch (IllegalArgumentException e) {
            throw new InvalidCarFileExceptions("Invalid docType: " + docType + ". Allowed values: " + Arrays.toString(DocType.values()));
        }
        if (repository.existsByCarIdAndDocType(carId, validatedDocType)) {
            throw new DuplicatePhotosException("Photo already exists for this car with docType " + docType);
        }
        if (file == null || file.isEmpty()) throw new InvalidCarFileExceptions("File is empty");
        if (file.getSize() > (5 * 1024 * 1024)) {
            throw new InvalidCarFileExceptions("File size exceeds 5 MB limit");
        }
        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equalsIgnoreCase("image/jpeg") ||
                        contentType.equalsIgnoreCase("image/png") ||
                        contentType.equalsIgnoreCase("image/jpg"))) {
            throw new InvalidCarFileExceptions("Invalid file type. Only jpg, jpeg, png allowed");
        }
        List<PremiumCarPhoto> exists = repository.findByCarAndDocType(car, DocType.valueOf(docType));
        if (!exists.isEmpty()) {
            throw new DuplicatePhotosException("Photo already exists for this car with docType " + docType);
        }
        String fileUrl = cloudinaryService.uploadFile(file, "premiumcars");

        PremiumCarPhoto photo = new PremiumCarPhoto();
        photo.setCar(car);
        photo.setDocType(DocType.valueOf(docType));
        photo.setFileSize(file.getSize());
        photo.setContentType(file.getContentType());
        photo.setUploadedAt(LocalDateTime.now());
        photo.setFileUrl(fileUrl);

        repository.save(photo);

        return new ApiResponseDto(
                "success",
                "The car photo added successfully for car id: " + carId,
                201,
                "CREATED",
                null
        );
    }

    @Override
    public ApiResponseDto updatePhoto(Long photoId, String docType, MultipartFile file) throws IOException {
        PremiumCarPhoto photo = repository.findById(photoId)
                .orElseThrow(() -> new PhotosNotFoundException("Photo not found with id: " + photoId));


        if (docType != null && !docType.isBlank()) {
            try {
                photo.setDocType(DocType.valueOf(docType));
            } catch (IllegalArgumentException e) {
                throw new InvalidCarFileExceptions("Invalid docType: " + docType + ". Allowed values: " + Arrays.toString(DocType.values()));
            }
        }

        if (file != null && !file.isEmpty()) {
            if (file.getSize() > (5 * 1024 * 1024)) {
                throw new InvalidCarFileExceptions("File size exceeds 5 MB limit");
            }
            String contentType = file.getContentType();
            if (contentType == null ||
                    !(contentType.equalsIgnoreCase("image/jpeg") ||
                            contentType.equalsIgnoreCase("image/png") ||
                            contentType.equalsIgnoreCase("image/jpg"))) {
                throw new InvalidCarFileExceptions("Invalid file type. Only jpg, jpeg, png allowed");
            }

            String fileUrl = cloudinaryService.uploadFile(file, "premiumcars");
            photo.setFileSize(file.getSize());
            photo.setContentType(file.getContentType());
            photo.setFileUrl(fileUrl);
        }

        repository.save(photo);

        return new ApiResponseDto(
                "success",
                "The car photo updated successfully for photo id: " + photo.getPhotoId(),
                200,
                "UPDATED",
                null
        );
    }
    @Override
    public Optional<PremiumCarPhotoDto> getPhotoById(Long photoId) {
        return repository.findById(photoId)
                .map(PremiumCarPhotoMapper::toDto);
    }

    @Override
    public List<PremiumCarPhotoDto> getPhotosByCar(int carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarsNotFoundException("Car not found with id: " + carId));

        return repository.findByCar(car).stream()
                .map(PremiumCarPhotoMapper::toDto)
                .toList();
    }

    @Override
    public List<PremiumCarPhotoDto> getPhotosByCarAndDocType(int carId, String docType) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarsNotFoundException("Car not found with id: " + carId));


        List<PremiumCarPhoto> photos = repository.findByCarAndDocType(car, DocType.valueOf(docType));
        if (photos.isEmpty()) throw new PhotosNotFoundException("No photos found for docType: " + docType);

        return photos.stream().map(PremiumCarPhotoMapper::toDto).toList();
    }
    @Override
    public void deletePhotoById(Long photoId) {
        if (!repository.existsById(photoId)) {
            throw new PhotosNotFoundException("Photo not found with id: " + photoId);
        }
        repository.deleteById(photoId);
    }

    @Override
    @Transactional
    public void deletePhotosByCar(int carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarsNotFoundException("Car not found with id: " + carId));

        repository.deleteByCar(car);
    }
}


