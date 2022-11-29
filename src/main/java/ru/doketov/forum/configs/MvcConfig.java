package ru.doketov.forum.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("user-main");
        registry.addViewController("/admin").setViewName("admin-main");
        registry.addViewController("/").setViewName("main");
        registry.addViewController("/registration").setViewName("registration");
    }
}
