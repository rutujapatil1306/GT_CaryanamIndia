package com.spring.jwt.PremiumCarBrandData;

import lombok.*;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor

public class AllBrandDTO<T>{
    private String message;
    private List<?> list;
    private String exception;


    public AllBrandDTO(String message, List<?> list, String exception) {
        this.message = message;
        this.list = list;
        this.exception = exception;
    }

    public static AllBrandDTO<List<String>> success(String s, List<String> subVariants) {
        return new AllBrandDTO<List<String>>(s, subVariants, null);
    }
}
