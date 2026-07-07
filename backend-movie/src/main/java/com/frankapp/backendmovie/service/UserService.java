package com.frankapp.backendmovie.service;

import com.frankapp.backendmovie.entity.User;
import com.frankapp.backendmovie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User create(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }
}

