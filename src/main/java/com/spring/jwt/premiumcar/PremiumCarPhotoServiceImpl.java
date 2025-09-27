package com.spring.jwt.premiumcar;
import com.spring.jwt.PremiumCarData.PremiumCar;
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
import java.util.List;
import java.util.Optional;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class PremiumCarPhotoServiceImpl implements PremiumCarPhotoService {

    private final PremiumCarPhotoRepository repository;
    private final PremiumCarsRepository premiumCarsRepository; //Changed from CarRepository
    private final CloudinaryService cloudinaryService;

    @Override
    public ApiResponseDto createPhoto(int premiumCarId, String docType, MultipartFile file) throws IOException {
        PremiumCar premiumCar = premiumCarsRepository.findById(premiumCarId)
                .orElseThrow(() -> new CarsNotFoundException("PremiumCar not found with id: " + premiumCarId));

        if (docType == null || docType.isBlank())
            throw new InvalidCarFileExceptions("docType is required");

        DocType validatedDocType;
        try {
            validatedDocType = DocType.valueOf(docType);
        } catch (IllegalArgumentException e) {
            throw new InvalidCarFileExceptions("Invalid docType: " + docType + ". Allowed values: " + Arrays.toString(DocType.values()));
        }

        if (repository.existsByPremiumCarAndDocType(premiumCar, validatedDocType)) {
            throw new DuplicatePhotosException("Photo already exists for this premium car with docType " + docType);
        }

        if (file == null || file.isEmpty())
            throw new InvalidCarFileExceptions("File is empty");

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

        PremiumCarPhoto photo = new PremiumCarPhoto();
        photo.setPremiumCar(premiumCar); // ✅ Link with PremiumCar
        photo.setDocType(validatedDocType);
        photo.setFileSize(file.getSize());
        photo.setContentType(file.getContentType());
        photo.setUploadedAt(LocalDateTime.now());
        photo.setFileUrl(fileUrl);

        repository.save(photo);

        return new ApiResponseDto(
                "success",
                "The premium car photo added successfully for premium car id: " + premiumCarId,
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
                "The premium car photo updated successfully for photo id: " + photo.getPhotoId(),
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
    public List<PremiumCarPhotoDto> getPhotosByCar(int premiumCarId) {
        PremiumCar premiumCar = premiumCarsRepository.findById(premiumCarId)
                .orElseThrow(() -> new CarsNotFoundException("PremiumCar not found with id: " + premiumCarId));

        return repository.findByPremiumCar(premiumCar).stream()
                .map(PremiumCarPhotoMapper::toDto)
                .toList();
    }

    @Override
    public List<PremiumCarPhotoDto> getPhotosByCarAndDocType(int premiumCarId, String docType) {
        PremiumCar premiumCar = premiumCarsRepository.findById(premiumCarId)
                .orElseThrow(() -> new CarsNotFoundException("PremiumCar not found with id: " + premiumCarId));

        List<PremiumCarPhoto> photos = repository.findByPremiumCarAndDocType(premiumCar, DocType.valueOf(docType));
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
    public void deletePhotosByCar(int premiumCarId) {
        PremiumCar premiumCar = premiumCarsRepository.findById(premiumCarId)
                .orElseThrow(() -> new CarsNotFoundException("PremiumCar not found with id: " + premiumCarId));

        repository.deleteByPremiumCar(premiumCar);
    }
}
