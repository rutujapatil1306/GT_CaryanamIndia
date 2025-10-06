package com.spring.jwt.CarPhoto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPhotoDto {


        private Integer id;
        private Integer car_id;
        private String photo_link;
        private String fileFormat;
        private String publicId;
        private LocalDateTime uploadedAt;
        private DocType type;
}
