package com.frankapp.backendmovie.repository;

import com.frankapp.backendmovie.entity.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
    // You can add custom query methods here if needed, e.g.:
     Flux<Movie> findByGenre(String genre);
}
