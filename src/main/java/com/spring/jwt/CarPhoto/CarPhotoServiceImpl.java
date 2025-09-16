package com.spring.jwt.CarPhoto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarPhotoServiceImpl implements CarPhotoService {


    @Autowired
    CarPhotoRepository carPhotoRepository;

    @Override
    public CarPhotoDto createCarPhoto(CarPhotoDto carPhotoDto) {

        return null;
    }
}
