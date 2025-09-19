package com.spring.jwt.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "save_car", uniqueConstraints = {@UniqueConstraint(columnNames = {"carId", "userId"})})
public class SaveCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saveCarId", nullable = false)
    private Integer saveCarId;

    private  Integer carId;

    private int userId;


}
