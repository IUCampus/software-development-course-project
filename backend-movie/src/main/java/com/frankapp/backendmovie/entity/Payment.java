package com.frankapp.backendmovie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Document(collection = "payments")
public class Payment {
    
    @Id
    private String id;

    private String userId;
    private String movieId; // optional: payment for a specific movie
    private BigDecimal amount;
    private String method; // e.g., "CARD", "PAYPAL"
    private String status; // e.g., "PENDING", "COMPLETED", "FAILED"
    private Instant createdAt;

    public Payment() {
    }

    public Payment(String id, String userId, String movieId, BigDecimal amount, String method, String status, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.createdAt = createdAt;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
