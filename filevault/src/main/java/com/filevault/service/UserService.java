package com.filevault.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.filevault.model.User;
import com.filevault.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User save(User user) {
        return userRepo.save(user);
    }
}