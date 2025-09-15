package com.spring.jwt.PremiumCarData;

import com.spring.jwt.PremiumCarBrandData.AllBrandDTO;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PremiumDTO<T> {

    private String message;
    private List<?> list;
    private String exception;
    private String noOfPremiumCar;// optional field


    public static PremiumDTO success(String message, Object object) {
        return new PremiumDTO(message, List.of(object), null, null);
    }
}



