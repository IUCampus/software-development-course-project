package com.frankapp.backendmovie.repository;

import com.frankapp.backendmovie.entity.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {

    // Custom query automatically mapped by Spring Data MongoDB
    Mono<Boolean> existsByMovieIdAndSeatNumberAndStatus(String movieId, String seatNumber, String status);
}
