package com.filevault.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.filevault.model.User;
import com.filevault.repository.UserRepository;

import jakarta.annotation.PostConstruct;

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

    public User registerUser(String username, String rawPassword, String role) {
    String encoded = new BCryptPasswordEncoder().encode(rawPassword);
    User user = new User(username, encoded, role);
    return userRepo.save(user);
    }

    @PostConstruct
    public void initAdmin() {
        if (!userRepo.findByUsername("admin").isPresent()) {
            registerUser("admin", "admin123", "ROLE_ADMIN");
            System.out.println("✅ Default admin user created: admin/admin123");
        }
    }
}

