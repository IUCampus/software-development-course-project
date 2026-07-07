package com.frankapp.backendmovie.repository;

import com.frankapp.backendmovie.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findByUserId(String userId);
    
    List<Payment> findByMovieId(String movieId);
}
