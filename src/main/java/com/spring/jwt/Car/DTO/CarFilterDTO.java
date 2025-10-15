package com.spring.jwt.Car.DTO;
import com.spring.jwt.Car.CarStatus;

public class CarFilterDTO {
    private CarStatus status;
    private String brand;
    private String model;
    private String city;
    private String fuelType;
    private String transmission;
    private Integer minPrice;
    private Integer maxPrice;

    // Getters and Setters
    public CarStatus getStatus() { return status; }
    public void setStatus(CarStatus status) { this.status = status; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }
    public Integer getMinPrice() { return minPrice; }
    public void setMinPrice(Integer minPrice) { this.minPrice = minPrice; }
    public Integer getMaxPrice() { return maxPrice; }
    public void setMaxPrice(Integer maxPrice) { this.maxPrice = maxPrice; }
}
