package com.Cart.Micro.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {

    // to create bean for model mapper
    // it is read only class, so we cant use component annotation on that class
    // to create bean.

    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }

}
