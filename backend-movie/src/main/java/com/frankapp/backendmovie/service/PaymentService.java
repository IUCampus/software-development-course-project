package com.frankapp.backendmovie.service;

import com.frankapp.backendmovie.entity.Payment;
import com.frankapp.backendmovie.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getById(String id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getByUserId(String userId) {
        return paymentRepository.findByUserId(userId);
    }

    public List<Payment> getByMovieId(String movieId) {
        return paymentRepository.findByMovieId(movieId);
    }

    public Payment create(Payment payment) {
        payment.setId(null);
        return paymentRepository.save(payment);
    }

    public Payment update(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void delete(String id) {
        paymentRepository.deleteById(id);
    }
}
