package com.spring.jwt.CarView;

import com.spring.jwt.CarView.DTO.CarViewDto;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.CarView;
import com.spring.jwt.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CarViewMapper {

        public CarViewDto toDto(CarView carView)
        {
            if(carView == null)
            {
                return null;
            }

            try{
                CarViewDto dto = new CarViewDto();
                dto.setId((carView.getId()));
                dto.setCount(carView.getCount());
                dto.setLastViewedAt(carView.getLastViewedAt());
                if (carView.getUser() != null) {
                    dto.setUser_id((carView.getUser().getId()));
                }
                if (carView.getCar() != null){
                    dto.setCar_id(carView.getCar().getId());
                }
                log.debug("Mapped Car To DTO: {}", dto);
                return dto;
            }
            catch(Exception ex)
            {
                log.error("Error Converting CarView To Dto: {}", ex.getMessage(),ex);
                throw new RuntimeException("Error converting CarView to DTO", ex);
            }
        }

        public CarView toEntity(CarViewDto carViewDto , User user, Car car)
        {
            if(carViewDto == null)
            {
                return null;
            }
            try{
                CarView view = new CarView();
                view.setId(carViewDto.getId());
                view.setCount(carViewDto.getCount());
                view.setLastViewedAt(carViewDto.getLastViewedAt());
                view.setUser(user);
                view.setCar(car);
                return view;
            }
            catch(Exception ex)
            {
                log.error("Error Converting Dto To CarView: {}", ex.getMessage(),ex);
                throw new RuntimeException("Error Converting Dto To CarView", ex);

            }
        }
}
