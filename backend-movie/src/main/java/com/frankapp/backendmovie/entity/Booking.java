package com.frankapp.backendmovie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "bookings") // Marks this class as a MongoDB document
public class Booking {

    @Id
    private String id; // MongoDB auto-generates a unique String ObjectId

    private String userId;
    private String movieId;
    private String seatNumber;
    private String status; // PENDING_PAYMENT, CONFIRMED, CANCELLED
    private LocalDateTime bookingTime;
    private Double amount;

    public Booking() {
    }

    public Booking(String id, String userId, String movieId, String seatNumber, String status, LocalDateTime bookingTime, Double amount) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.bookingTime = bookingTime;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
