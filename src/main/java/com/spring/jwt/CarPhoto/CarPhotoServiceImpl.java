package com.spring.jwt.CarPhoto;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.spring.jwt.Car.CarRepository;
import com.spring.jwt.Car.Exception.CarNotFoundException;
import com.spring.jwt.CarPhoto.DTO.CarPhotoDto;
import com.spring.jwt.CarPhoto.Exception.InvalidFileException;
import com.spring.jwt.CarPhoto.Exception.PhotoNotFoundException;
import com.spring.jwt.CarPhoto.Exception.TypeMisMatchException;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.CarPhoto;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarPhotoServiceImpl implements CarPhotoService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    @Qualifier("CarCloudinary")
    Cloudinary cloudinary;

    @Autowired
    CarPhotoRepository carPhotoRepository;

    @Autowired
    CarPhotoMapper carPhotoMapper;

    private void validateFileFormat (List<MultipartFile> files) {
//        if (files == null || files.isEmpty()) {
//            throw new InvalidFileException("No Files Provided to upload");
//        }
        for(MultipartFile file : files) {

           validateFormat(file);
        }
    }
    public void validateFormat(MultipartFile file)
    {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("No File Provided to upload");
        }

            if (file.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("File must be <= 5 MB");
            }
            String contentType = file.getContentType();
            if (contentType == null ||
                    !(contentType.equals("image/jpeg") ||
                            contentType.equals("image/jpg") ||
                            contentType.equals("image/png") ||
                            contentType.equals("image/webp"))) {
                throw new InvalidFileException("Only JPEG, JPG, PNG, and WEBP formats are allowed.");
            }
    }

    @Override
    public List<CarPhotoDto> uploadCarPhotos(Integer carId, List<MultipartFile> files, DocType type) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car not Found at given Id : " + carId));
        validateFileFormat(files);


        if(type == DocType.COVER) {
            if(files.size() > 1)
            {
                throw new InvalidFileException("Only one Cover Photo Allowed for one Car");
            }
            CarPhoto existingCoverImage = carPhotoRepository.findByCarIdAndType(carId, DocType.COVER).orElse(null);
            if (existingCoverImage != null) {
                carPhotoRepository.delete(existingCoverImage);
                deleteFromCloudinary(existingCoverImage.getPublicId());
            }
            try {
                List<CarPhoto> photos = new ArrayList<>();
                for(MultipartFile file: files) {
                    Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                            ObjectUtils.asMap("folder", "car_photos"));
                    String url = (String) uploadResult.get("secure_url");
                    String publicId = (String) uploadResult.get("public_id");

                    System.out.println("Upload Result: " + uploadResult);
                    String contentType = file.getContentType();
                    String fileFormat = null;
                    if (contentType != null && contentType.startsWith("image/")) {
                        fileFormat = contentType.substring(contentType.lastIndexOf("/") + 1);
                    } else {
                        throw new InvalidFileException("Invalid file format. Only images are allowed.");
                    }

                    CarPhoto carPhoto = new CarPhoto();
                    carPhoto.setCar(car);
                    carPhoto.setPhoto_link(url);
                    carPhoto.setFileFormat(fileFormat);
                    carPhoto.setType(type);
                    carPhoto.setUploadedAt(LocalDateTime.now());
                    carPhoto.setPublicId(publicId);

                    CarPhoto saved = carPhotoRepository.save(carPhoto);
                    photos.add(saved);
                    return photos.stream().map(carPhotoMapper::toDto).toList();
                }


            }catch(Exception ex){
                throw new RuntimeException("Failed To upload Cover Image "  + ex.getMessage(), ex);
            }
        }


        else if (type == DocType.ADDITIONAL){
            try {
                List<CarPhoto> additionalPhotos = new ArrayList<>();
                for(MultipartFile file : files) {
                    Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                            ObjectUtils.asMap("folder", "car_photos"));
                    String url = (String) uploadResult.get("secure_url");
                    String publicId = (String) uploadResult.get("public_id");
                    String contentType = file.getContentType();
                    String fileFormat = null;
                    if (contentType != null || contentType.startsWith("image/")) {
                        fileFormat = contentType.substring(contentType.lastIndexOf("/") + 1);
                    } else {
                        throw new InvalidFileException("Invalid file format. Only images are allowed.");
                    }
                    CarPhoto carPhoto = new CarPhoto();
                    carPhoto.setCar(car);
                    carPhoto.setPhoto_link(url);
                    carPhoto.setFileFormat(fileFormat);
                    carPhoto.setType(type);
                    carPhoto.setUploadedAt(LocalDateTime.now());
                    carPhoto.setPublicId(publicId);

                    CarPhoto saved = carPhotoRepository.save(carPhoto);
                    additionalPhotos.add(saved);
                    System.out.println("Uploading File: " + file.getOriginalFilename());


                }
                return additionalPhotos.stream().map(carPhotoMapper::toDto).toList();
            }catch(Exception ex) {
                throw new RuntimeException("Failed to upload  image " + ex.getMessage(), ex);
            }
        }


        return null;
    }



    // here id is PhotoId
    @Override
    public void deleteCarPhoto(Integer id) {

        CarPhoto photo = carPhotoRepository.findById(id).orElseThrow(() -> new PhotoNotFoundException("Photo not found at given Id : " + id));

        // deleted one photo by photo id from database
        carPhotoRepository.delete(photo);

        //deleted one photo by photo id from cloudinary
        deleteFromCloudinary(photo.getPublicId());
    }
    private void deleteFromCloudinary(String publicId)
    {
        try {
            if (publicId != null && !publicId.isEmpty()) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch(Exception ex)
            {
                throw new RuntimeException("Failed to Delete CarPhoto from Cloudinary" + ex.getMessage());
            }
    }

    @Override
    public CarPhotoDto getCarPhotoById(Integer id) {
        CarPhoto carPhoto = carPhotoRepository.findById(id)
                .orElseThrow(() -> new PhotoNotFoundException("Car Photo not found for ID: " + id));

        return carPhotoMapper.toDto(carPhoto);
    }

    @Override
    public CarPhotoDto updateCarPhotoByCarId(Integer carId, Integer photoId, MultipartFile imageFile, DocType type) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car Not Found at Id: " + carId));


            CarPhoto photo = carPhotoRepository.findById(photoId).orElseThrow(() -> new PhotoNotFoundException("Cover Photo not found for CarId : " + carId));
            boolean updated = false;
            if( imageFile != null && !imageFile.isEmpty())
            {

                validateFormat(imageFile);
                deleteFromCloudinary(photo.getPublicId());
                try
                {
                    Map uploadresult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.asMap("folder", "car_photos"));
                    photo.setPhoto_link( (String) uploadresult.get("secure_url"));
                    photo.setPublicId( (String) uploadresult.get("public_id"));
                    photo.setUploadedAt(LocalDateTime.now());
                    photo.setFileFormat( (String) uploadresult.get("format"));
                    updated = true;

                }
                catch(Exception ex)
                {
                    throw new RuntimeException("Failed to update car photo");
                }
            }
            if(type != null)
            {
                if(photo.getType() == type)
                {
                    throw new IllegalArgumentException("Type " + type + " is already present. No need to update");
                }
                photo.setType(type);
                updated = true;
            }

            if(!updated)
            {
                throw new IllegalArgumentException("No fields are provided to update (TYPE and IMAGE FILE is Required) ");
            }


        CarPhoto updatedPhoto = carPhotoRepository.save(photo);
        return carPhotoMapper.toDto(updatedPhoto);
    }


    @Override
    public List<CarPhotoDto> getCarPhotosByCarId(Integer carId){
        List<CarPhoto> photos = carPhotoRepository.findByCarId(carId);
        if (photos.isEmpty()) {
            throw new PhotoNotFoundException("No photos found for carId: " + carId);
        }

        return photos.stream()
                .map(carPhotoMapper::toDto)
                .collect(Collectors.toList());

    }
    public void deleteCarPhotosByCarId(Integer carId) {
        List<CarPhoto> photos = carPhotoRepository.findByCarId(carId);

        if (photos.isEmpty()) {
            throw new PhotoNotFoundException("No photos found for carId: " + carId);
        }
        List<String> publicIds = photos.stream()
                .map(CarPhoto::getPublicId)
                .filter(publicId -> publicId != null)
                .toList();

        if (!publicIds.isEmpty()) {
            try {
                cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());
            } catch (Exception e) {
                throw new RuntimeException("Failed to bulk delete Cloudinary files");

            }
        }
        carPhotoRepository.deleteByCarId(carId);
    }


}








//    @Override
//    public CarPhotoDto uploadCarPhoto(Integer carId, MultipartFile imageFile, DocType type) {
//        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car Not Found for given CarId: " + carId));
//
//        try {
//            if (imageFile == null || imageFile.isEmpty()) {
//                throw new InvalidFileException("File cannot be empty");
//            }
//            validateFileFormat(imageFile);
//
//            Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
//                        ObjectUtils.asMap("folder", "car_photos"));
//
//                String url = (String) uploadResult.get("secure_url");
//
//                String contentType = imageFile.getContentType();
//                String fileFormat = null;
//                if (contentType != null && contentType.startsWith("image/")) {
//                    fileFormat = contentType.substring(contentType.lastIndexOf("/") + 1);
//                } else {
//                    throw new InvalidFileException("Invalid file format. Only images are allowed.");
//                }
//                CarPhoto carPhoto = new CarPhoto();
//                carPhoto.setCar(car);
//                carPhoto.setPhoto_link(url);
//                carPhoto.setType(type);
//                carPhoto.setFileFormat(fileFormat);
//                carPhoto.setUploadedAt(LocalDateTime.now());
//
//
//                CarPhoto saved = carPhotoRepository.save(carPhoto);
//                return carPhotoMapper.toDto(saved);
//
//            }
//            catch (IOException e) {
//                throw new RuntimeException("Failed To upload Image " + e.getMessage(), e);
//            }
//    }



//
//    @Override
//    public CarPhotoDto updateCarPhotoById(Integer id, MultipartFile imageFile, DocType type) {
//        CarPhoto carPhoto = carPhotoRepository.findById(id)
//                .orElseThrow(() -> new PhotoNotFoundException("Car Photo not found for ID: " + id));
//
//        Car car = carPhoto.getCar();
//        if (type != null) {
//
//            if (type.equals(carPhoto.getType())) {
//                throw new DuplicatePhotoException("Car photo already has type " + type);
//            }
//                boolean typeExists = carPhotoRepository.existsByCarIdAndTypeAndIdNot(car.getId(), type, id);
//                if (typeExists) {
//                    throw new DuplicatePhotoException("Car already has a photo of type " + type);
//                }
//                carPhoto.setType(type);
//
//        }
//        if (imageFile != null && !imageFile.isEmpty()) {
//            validateFileFormat(imageFile);
//
//            try {
//
//                Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
//                        ObjectUtils.asMap("folder", "car_photos"));
//
//                String url = (String) uploadResult.get("secure_url");
//                String format = (String) uploadResult.get("format");
//
//                carPhoto.setPhoto_link(url);
//                carPhoto.setFileFormat(format);
//                carPhoto.setUploadedAt(LocalDateTime.now());
//
//
//            } catch (IOException e) {
//                throw new InvalidFileException("Failed to upload new file");
//            }
//        }
//
//
//        CarPhoto saved = carPhotoRepository.save(carPhoto);
//        return carPhotoMapper.toDto(saved);
//    }
//
//    @Override
//        public CarPhotoDto updateCarPhotoByCarId (Integer carId, MultipartFile imageFile, DocType type){
//            Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car Not Found At carId : " + carId));
//            CarPhoto carPhoto = carPhotoRepository.findByCarId(carId).orElseThrow(() -> new PhotoNotFoundException("Photo not found for CarId: " + carId));
//
//        if ((imageFile == null || imageFile.isEmpty()) && type == null) {
//            throw new IllegalArgumentException("No new photo file or type provided for update");
//        }
//        boolean updated = false;
//
//        if (imageFile != null && !imageFile.isEmpty()) {
//                validateFileFormat(imageFile);
//                try {
//                        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
//                                ObjectUtils.asMap("folder", "car_photos"));
//
//                        String url = (String) uploadResult.get("secure_url");
//                        String fileFormat = (String) uploadResult.get("format");
//                        carPhoto.setPhoto_link(url);
//                        carPhoto.setFileFormat(fileFormat);
//                        carPhoto.setUploadedAt(LocalDateTime.now());
//                        updated = true;
//
//                } catch (IOException e) {
//                    throw new RuntimeException("Failed to upload new File " + e.getMessage(), e);
//                }
//
//            }
//                if (type != null) {
//                    if (type.equals(carPhoto.getType())) {
//                        throw new DuplicatePhotoException("This type is already set for this car photo.");
//                    }
//
//                    boolean duplicateType = carPhotoRepository.existsByCarIdAndTypeAndIdNot(carId, type, carPhoto.getId());
//                    if (duplicateType) {
//                        throw new DuplicatePhotoException("This photo with type " + type + " already exists for this car.");
//                    }
//                    carPhoto.setType(type);
//                    updated = true;
//                }
//
//
//                if (!updated) {
//                    throw new DuplicatePhotoException("No changes detected to update. Either same file or same type.");
//                }
//
//                    CarPhoto saved = carPhotoRepository.save(carPhoto);
//                    return carPhotoMapper.toDto(saved);
//    }
//
//        @Override
//        public void deleteCarPhoto (Integer id,boolean hardDelete){
//            CarPhoto carPhoto = carPhotoRepository.findById(id).orElseThrow(() -> new PhotoNotFoundException("Photo Not Found for id: " + id));
//
//            if (hardDelete) {
//                carPhotoRepository.delete(carPhoto);
//            } else {
//                carPhoto.setStatus(CarPhotoStatus.DELETED);
//                carPhotoRepository.save(carPhoto);
//            }
//        }
//
//        @Override
//        public void deleteCarPhotoByCarId (Integer carId, DocType type,boolean hardDelete){
//
//            Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car not Found for id: " + carId));
//
//            CarPhoto carPhoto = carPhotoRepository.findByCarIdAndType(carId, type).orElseThrow(() -> new PhotoNotFoundException("Photo not Found for carId: " + carId));
//
//            if (hardDelete) {
//                carPhotoRepository.delete(carPhoto);
//            } else {
//                carPhoto.setStatus(CarPhotoStatus.DELETED);
//                carPhotoRepository.save(carPhoto);
//            }
//        }
//
//        private void validateFileFormat (MultipartFile imageFile){
//            String contentType = imageFile.getContentType();
//            if (contentType == null ||
//                          !(contentType.equals("image/jpeg") ||
//                            contentType.equals("image/jpg") ||
//                            contentType.equals("image/png") ||
//                            contentType.equals("image/webp"))) {
//                throw new InvalidFileException("Only JPEG, JPG, PNG, and WEBP formats are allowed.");
//            }
//        }
//}
