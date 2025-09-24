package com.spring.jwt.PremiumCarBrandData;

import lombok.*;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllBrandDTO<T>{
    private String message;
    private T data;         // was list, now generic data
    private String exception;

    public AllBrandDTO(String message) {

    }


    public  static <T>AllBrandDTO success(String message,T data){
       return new AllBrandDTO(message,data,null);
    }
    public static <T> AllBrandDTO<T> error(String message,String exception){
       return new AllBrandDTO(message,null,exception);
    }
    public static <T> AllBrandDTO<T> error(String message){
       return new AllBrandDTO(message);
    }
    }

