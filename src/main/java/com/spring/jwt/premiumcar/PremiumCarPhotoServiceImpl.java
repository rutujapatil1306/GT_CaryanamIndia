package com.spring.jwt.premiumcar;
import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.entity.Car;
import com.spring.jwt.premiumcar.exceptions.CarsNotFoundException;
import com.spring.jwt.premiumcar.exceptions.DuplicatePhotoException;
import com.spring.jwt.premiumcar.exceptions.InvalidFileException;
import com.spring.jwt.premiumcar.exceptions.PhotoNotFoundException;
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
    private final CarRepository carRepository;
    private final CloudinaryService cloudinaryService;

    @Override public PremiumCarPhotoDto createPhoto(int carId, String docType, MultipartFile file) throws IOException {
        Car car = carRepository.findById(carId) .orElseThrow(() -> new CarsNotFoundException("Car not found with id: " + carId));
        if (file.isEmpty()) throw new InvalidFileException("File is empty");
        if (docType == null || docType.isBlank()) throw new InvalidFileException("docType is required");
        List<PremiumCarPhoto> exists = repository.findByCarAndDocType(car, DocType.valueOf(docType));
        if (!exists.isEmpty()) {
            throw new DuplicatePhotoException("Photo already exists for this car with docType " + docType);
        }

        String fileUrl = cloudinaryService.uploadFile(file, "premiumcars");
        PremiumCarPhoto photo = new PremiumCarPhoto(); photo.setCar(car);

        photo.setDocType(DocType.valueOf(docType));
        photo.setFileSize(file.getSize()); photo.setContentType(file.getContentType());
        photo.setUploadedAt(LocalDateTime.now());
        photo.setFileUrl(fileUrl);
        return PremiumCarPhotoMapper.toDto(repository.save(photo));
    }
    @Override public PremiumCarPhotoDto updatePhoto(Long photoId, String docType, MultipartFile file) throws IOException
    {
        PremiumCarPhoto photo = repository.findById(photoId) .orElseThrow(() -> new PhotoNotFoundException("Photo not found with id: " + photoId));
        if (docType != null && !docType.isBlank()) {
            photo.setDocType(DocType.valueOf(docType));
        }
        if (file != null && !file.isEmpty()) {
            String fileUrl = cloudinaryService.uploadFile(file, "premiumcars");
            photo.setFileSize(file.getSize()); photo.setContentType(file.getContentType());
            photo.setFileUrl(fileUrl);
        } return PremiumCarPhotoMapper.toDto(repository.save(photo)); }

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
        if (photos.isEmpty()) throw new PhotoNotFoundException("No photos found for docType: " + docType);

        return photos.stream().map(PremiumCarPhotoMapper::toDto).toList();
    }
    @Override
    public void deletePhotoById(Long photoId) {
        if (!repository.existsById(photoId)) {
            throw new PhotoNotFoundException("Photo not found with id: " + photoId);
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


