package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JULY;
import static java.time.Month.MAY;

@Configuration
public class StudentConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student tornike = new Student(
                    1L,
                    null,
                    "tornike.davitashvili3@gmail.com",
                    LocalDate.of(2003, MAY,27)
            );
            Student alex = new Student(
                    2L,
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(1987, JULY,20)
            );
            repository.saveAll(
                    List.of(tornike,alex)
            );
        };
    }
}
