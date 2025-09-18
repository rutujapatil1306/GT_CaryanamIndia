package com.spring.jwt.premiumcar;

import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.premiumcar.PremiumCarPhoto;
import com.spring.jwt.premiumcar.PremiumCarPhotoRepository;
import com.spring.jwt.premiumcar.PremiumCarPhotoService;
import com.spring.jwt.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PremiumCarPhotoServiceImpl implements PremiumCarPhotoService {

    private final PremiumCarPhotoRepository repository;
    private final CarRepository carRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public PremiumCarPhotoDto createPhoto(int carId, String docType, MultipartFile file) throws IOException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid carId"));

        if (file.isEmpty()) throw new IllegalArgumentException("File is empty");
        if (docType == null || docType.isBlank()) throw new IllegalArgumentException("docType required");

        String fileUrl = cloudinaryService.uploadFile(file, "premiumcars");

        PremiumCarPhoto photo = new PremiumCarPhoto();
        photo.setCar(car);
        photo.setDocType(DocType.valueOf(docType));
        photo.setFileSize(file.getSize());
        photo.setContentType(file.getContentType());
        photo.setUploadedAt(LocalDateTime.now());
        photo.setFileUrl(fileUrl);

        return PremiumCarPhotoMapper.toDto(repository.save(photo));
    }

    @Override
    public PremiumCarPhotoDto updatePhoto(Long photoId, String docType, MultipartFile file) throws IOException {
        PremiumCarPhoto photo = repository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("Photo not found"));

        if (docType != null && !docType.isBlank()) {
            photo.setDocType(DocType.valueOf(docType));
        }

        if (file != null && !file.isEmpty()) {
            String fileUrl = cloudinaryService.uploadFile(file, "premiumcars");
            photo.setFileSize(file.getSize());
            photo.setContentType(file.getContentType());
            photo.setFileUrl(fileUrl);
        }

        return PremiumCarPhotoMapper.toDto(repository.save(photo));
    }

    @Override
    public Optional<PremiumCarPhotoDto> getPhotoById(Long photoId) {
        return repository.findById(photoId)
                .map(PremiumCarPhotoMapper::toDto);
    }

    @Override
    public List<PremiumCarPhotoDto> getPhotosByCar(int carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid carId"));

        return repository.findByCar(car).stream()
                .map(PremiumCarPhotoMapper::toDto)
                .toList();
    }
}


