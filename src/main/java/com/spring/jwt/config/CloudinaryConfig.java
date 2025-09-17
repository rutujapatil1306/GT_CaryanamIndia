package com.spring.jwt.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary getCloudinary()
    {
        Map config = new HashMap();
        config.put("cloud_name","dqpj2z8z4");
        config.put("api_key","756198799599931");
        config.put("api_secret","ABbkFnz7_U-UB4SXwOh_mgnLlS4");
        config.put("secure",true);
        return new Cloudinary (config);
    }
}
