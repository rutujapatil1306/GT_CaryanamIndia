package com.spring.jwt.PremiumCarBrandData;

import lombok.*;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
<<<<<<< HEAD

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
=======
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

>>>>>>> f6478de2863350de09dee9e4d298974975739906
