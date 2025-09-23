package com.spring.jwt.premiumcar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.jwt.entity.Car; // existing Car entity
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "premium_car_photos")
public class PremiumCarPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type", length = 50, nullable = false)
    private DocType docType;


    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    // Constructors, Getters, Setters
    public PremiumCarPhoto() {}


    public Long getPhotoId() { return photoId; }
    public void setPhotoId(Long photoId) { this.photoId = photoId; }
    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
    public DocType getDocType() { return docType; }
    public void setDocType(DocType docType) { this.docType = docType; }
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}

