package edu.uoc.tfgmonitorsystem.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/*").addResourceLocations("/");
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");

        registry.addViewController("/{spring:\\w+}").setViewName("forward:/");
        registry.addViewController("/**/{spring:\\w+}").setViewName("forward:/");
        registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}").setViewName("forward:/");
    }

}