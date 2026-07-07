package com.frankapp.backendmovie.service;

import com.frankapp.backendmovie.entity.Booking;
import com.frankapp.backendmovie.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Mono<Booking> createBooking(String userId, String movieId, String seatNumber) {

        // 1. Check if the seat is already taken
        return bookingRepository.existsByMovieIdAndSeatNumberAndStatus(movieId, seatNumber, "CONFIRMED")
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Seat " + seatNumber + " is already booked for this movie."));
                    }

                    // 2. Create the document
                    Booking booking = new Booking();
                    booking.setUserId(userId);
                    booking.setMovieId(movieId);
                    booking.setSeatNumber(seatNumber);
                    booking.setStatus("PENDING_PAYMENT");
                    booking.setAmount(15.00); // Standard ticket price
                    booking.setBookingTime(LocalDateTime.now());

                    // 3. Save to MongoDB
                    return bookingRepository.save(booking);
                })
                .flatMap(savedBooking -> {
                    // 4. Publish Event to Kafka (using fromFuture for non-blocking)
                    String eventPayload = String.format("{\"bookingId\": \"%s\", \"userId\": \"%s\", \"amount\": %.2f}",
                            savedBooking.getId(), userId, savedBooking.getAmount());

                    return Mono.fromFuture(() -> kafkaTemplate.send("booking-created-topic", eventPayload))
                            .thenReturn(savedBooking);
                });
    }
}
