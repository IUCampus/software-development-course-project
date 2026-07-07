package com.frankapp.backendmovie.controller;

import com.frankapp.backendmovie.entity.Movie;
import com.frankapp.backendmovie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/movies")
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/all")
    public Flux<Movie> getAllMovies() {
        return movieRepository.findAll()
                .doOnSubscribe(s -> logger.info("Fetching all movies"))
                .doOnNext(movie -> logger.info("Fetched movie: {}", movie.getTitle()))
                .doOnError(e -> logger.error("Error fetching movies: ", e))
                .switchIfEmpty(Flux.defer(() -> {
                    logger.warn("No movies found in database");
                    return Flux.empty();
                }))
                .onErrorResume(e -> {
                    logger.error("Returning empty flux due to error: {}", e.getMessage());
                    return Flux.empty();
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Movie>> getMovieById(@PathVariable String id) {
        return movieRepository.findById(id)
                .doOnNext(movie -> logger.info("Fetched movie by id {}: {}", id, movie.getTitle()))
                .doOnError(e -> logger.error("Error fetching movie by id {}: ", id, e))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    logger.error("Returning 500 due to error for id {}: {}", id, e.getMessage());
                    return Mono.just(ResponseEntity.status(500).build());
                });
    }
}
