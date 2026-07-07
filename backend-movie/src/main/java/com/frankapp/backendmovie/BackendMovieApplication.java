package com.frankapp.backendmovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.frankapp.backendmovie.repository",
        includeFilters = @org.springframework.context.annotation.ComponentScan.Filter(
                type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE,
                classes = {
                        com.frankapp.backendmovie.repository.UserRepository.class,
                        com.frankapp.backendmovie.repository.PaymentRepository.class,
                        com.frankapp.backendmovie.repository.ReviewRepository.class
                }))
@EnableReactiveMongoRepositories(basePackages = "com.frankapp.backendmovie.repository",
        includeFilters = @org.springframework.context.annotation.ComponentScan.Filter(
                type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE,
                classes = {
                        com.frankapp.backendmovie.repository.MovieRepository.class,
                        com.frankapp.backendmovie.repository.BookingRepository.class
                }))
public class BackendMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendMovieApplication.class, args);
    }

}
