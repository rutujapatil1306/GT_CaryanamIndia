package com.spring.jwt.premiumcar;

import java.time.LocalDateTime;

public class PremiumCarPhotoDto {

    private Long id;
    private int carId;            // Instead of full Car object
    private String docType;
    private long fileSize;
    private String contentType;
    private String fileUrl;
    private LocalDateTime uploadedAt;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }

    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }

    public long getFileSize() { return fileSize; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}
