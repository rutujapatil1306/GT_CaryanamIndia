package com.spring.jwt.entity;


import com.spring.jwt.CarPhoto.DocType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carphoto")
public class CarPhoto {
    @Id
    @Column(name = "car_photo_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "Car_Id", nullable = false)
    private Car car;

    @Column(name = "PhotoLink", nullable = false)
    private String photo_link;

    @Enumerated(EnumType.STRING)
    @Column(name = "PhotoType", nullable = false)
    private DocType type;
}