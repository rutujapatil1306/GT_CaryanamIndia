package com.spring.jwt.CarPhoto;

import com.spring.jwt.entity.CarPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CarPhotoMapper {

    public CarPhotoDto toDto(CarPhoto carPhoto)
    {
        if(carPhoto == null)
        {
            return null;
        }

        try
        {
            CarPhotoDto dto = new CarPhotoDto();

            dto.setId(carPhoto.getId());

            dto.setPhoto_link(carPhoto.getPhoto_link());

            dto.setType(carPhoto.getType());

            dto.setFileFormat(carPhoto.getFileFormat());

            dto.setUploadedAt(carPhoto.getUploadedAt());

            if(carPhoto.getCar() != null)
            {
                dto.setCar_id(carPhoto.getCar().getId());
            }
            log.debug("Mapped CarPhoto to Dto :{}", dto);

            return dto;
        }
        catch(Exception ex)
        {
            log.error("Error Converting CarPhoto To Dto: {}", ex.getMessage(),ex);
            throw new RuntimeException("Error converting CarPhoto to DTO", ex);
        }
    }

    public CarPhoto toEntity(CarPhotoDto carPhotoDto)
    {
        if(carPhotoDto == null)
        {
            return null;
        }

        try {
            CarPhoto photo = new CarPhoto();

            photo.setId(carPhotoDto.getId());

            photo.setPhoto_link(carPhotoDto.getPhoto_link());

            photo.setType(carPhotoDto.getType());

            photo.setFileFormat(carPhotoDto.getFileFormat());

            photo.setUploadedAt(carPhotoDto.getUploadedAt());

            return  photo;
        }
        catch(Exception ex)
        {
            log.error("Error Converting Dto to CarPhoto: {}", ex.getMessage(),ex);
            throw new RuntimeException("Error converting Dto to CarPhoto", ex);
        }

    }
}
