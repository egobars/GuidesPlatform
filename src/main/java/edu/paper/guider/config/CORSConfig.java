package edu.paper.guider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //.allowedOrigins("https://guidesplatform-production.up.railway.app")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH");
    }

}