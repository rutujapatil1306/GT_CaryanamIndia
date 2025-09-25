package com.spring.jwt.entity;
import com.spring.jwt.CarPhoto.CarPhotoStatus;
import com.spring.jwt.CarPhoto.DocType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_photo",
        uniqueConstraints = {@UniqueConstraint(name = "UK_car_photo", columnNames = {"car_id", "photo_type"})
    })


public class CarPhoto {
    @Id
    @Column(name = "car_photo_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;



    @NotBlank(message = "Photo link cannot be blank")
    @Size(max = 500, message = "Photo link cannot exceed 500 characters")
    @Column(name = "PhotoLink", nullable = false)
    private String photo_link;

    @Column(name = "FileFormat", nullable = false)
    @NotBlank(message = "File format is required")
    @Pattern(
            regexp = "^(jpg|jpeg|png|webp)$",
            message = "File format must be one of: jpg, jpeg, png, webp")
    private String fileFormat;

    @Column(name = "UploadedAt", nullable = false, updatable = false)
    @NotNull(message = "Uploaded time is required")
    private LocalDateTime uploadedAt;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Photo type is required")

    @Column(name = "PhotoType", nullable = false, length = 50)
    private DocType type;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Enumerated(EnumType.STRING)
    private CarPhotoStatus status;

}