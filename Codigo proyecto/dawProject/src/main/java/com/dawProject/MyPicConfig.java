package com.dawProject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyPicConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("El archivo de configuración ha entrado en vigor "); 
        registry.addResourceHandler("/static/img/**").addResourceLocations("file:/home/david/eclipse-workspaceSTS/dawProject/src/main/resources/static/img");
    }
}

