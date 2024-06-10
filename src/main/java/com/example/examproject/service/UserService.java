package com.example.examproject.service;

import com.example.examproject.model.User;
import com.example.examproject.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Attribut der holder reference til UserRepository
    private final UserRepository userRepository;

    // Konstruktør der initialiserer UserService med UserRepository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Metode til at registrere en ny bruger ved hjælp af UserRepository
    public User registerUser(User user) {
        return userRepository.registerUser(user);
    }

    // Metode til at finde en bruger baseret på ID ved hjælp af UserRepository
    public User findById(int id) {
        return userRepository.findById(id);
    }

    // Metode til at finde en bruger baseret på brugernavn ved hjælp af UserRepository
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Metode til at logge en bruger ind ved hjælp af UserRepository
    public User loginUser(String username, String password) {
        User userFromDb = userRepository.findByUsername(username);
        if (userFromDb != null && userFromDb.getPassword().equals(password)) {
            return userFromDb;
        }
        return null;
    }
}
