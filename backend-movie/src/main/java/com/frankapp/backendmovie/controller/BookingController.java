package com.frankapp.backendmovie.controller;

import com.frankapp.backendmovie.entity.Booking;
import com.frankapp.backendmovie.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Mono<ResponseEntity<Booking>> bookSeat(@RequestParam String userId,
                                            @RequestParam String movieId,
                                            @RequestParam String seatNumber) {
        return bookingService.createBooking(userId, movieId, seatNumber)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
