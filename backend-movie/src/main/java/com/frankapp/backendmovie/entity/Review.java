package com.frankapp.backendmovie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "reviews")
public class Review {
    
    @Id
    private String id;

    private String movieId;
    private String userId;
    private Integer rating; // 1-5
    private String comment;
    private Instant createdAt;

    public Review() {
    }

    public Review(String id, String movieId, String userId, Integer rating, String comment, Instant createdAt) {
        this.id = id;
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // Use a constructor or pre-save hook to initialize if needed, 
    // but for now, we'll keep it simple and just ensure we have constructors.
}
