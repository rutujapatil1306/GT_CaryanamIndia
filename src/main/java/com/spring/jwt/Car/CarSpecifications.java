package com.spring.jwt.Car;

import com.spring.jwt.Car.DTO.CarFilterDTO;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
public class CarSpecifications {

    public static Specification<Car> withFilters(CarFilterDTO filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // Status filter
            if (filter.getStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("carStatus"), filter.getStatus()));
            } else {
                predicate = cb.and(predicate, root.get("carStatus").in(Status.ACTIVE, Status.PENDING));
            }

            // Brand
            if (filter.getBrand() != null && !filter.getBrand().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("brand")), "%" + filter.getBrand().toLowerCase() + "%"));
            }

            // Model
            if (filter.getModel() != null && !filter.getModel().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("model")), "%" + filter.getModel().toLowerCase() + "%"));
            }

            // City
            if (filter.getCity() != null && !filter.getCity().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("city")), "%" + filter.getCity().toLowerCase() + "%"));
            }

            // FuelType
            if (filter.getFuelType() != null && !filter.getFuelType().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("fuelType")), "%" + filter.getFuelType().toLowerCase() + "%"));
            }

            // Transmission
            if (filter.getTransmission() != null && !filter.getTransmission().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("transmission")), "%" + filter.getTransmission().toLowerCase() + "%"));
            }

            // Price range
            if (filter.getMinPrice() != null) {
                predicate = cb.and(predicate, cb.ge(root.get("price"), filter.getMinPrice()));
            }
            if (filter.getMaxPrice() != null) {
                predicate = cb.and(predicate, cb.le(root.get("price"), filter.getMaxPrice()));
            }

            return predicate;
        };
    }
}
