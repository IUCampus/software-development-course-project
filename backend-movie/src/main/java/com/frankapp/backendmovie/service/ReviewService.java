package com.frankapp.backendmovie.service;

import com.frankapp.backendmovie.entity.Review;
import com.frankapp.backendmovie.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getById(String id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getByMovieId(String movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    public List<Review> getByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Review create(Review review) {
        review.setId(null);
        return reviewRepository.save(review);
    }

    public Review update(Review review) {
        return reviewRepository.save(review);
    }

    public void delete(String id) {
        reviewRepository.deleteById(id);
    }
}
