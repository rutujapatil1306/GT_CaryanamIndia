package com.spring.jwt.premiumcar;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new
                Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dfsgogjlr",
                "api_key", "398466398165673",
                "api_secret", "sRPKdx8Baq4DdH2dzm24E6i5KvA"
        ));
    }
}

