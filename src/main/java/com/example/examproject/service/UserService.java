// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.service'
package com.example.examproject.service;

// Importerer nødvendige klasser fra andre pakker
import com.example.examproject.model.User;
import com.example.examproject.repository.UserRepository;
import org.springframework.stereotype.Service;

// Denne linje fortæller, at denne klasse tilbyder tjenester relateret til brugere
@Service
public class UserService {

    // Her gemmer vi information om, hvordan vi arbejder med brugere
    private final UserRepository userRepository; // Gemmer en reference til UserRepository

    // Dette er en konstruktør, der bruges til at lave en ny UserService med en UserRepository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Denne metode registrerer en ny bruger ved hjælp af UserRepository
    public User registerUser(User user) {
        return userRepository.registerUser(user); // Kalder registerUser metoden i UserRepository
    }

    // Denne metode finder en bruger baseret på ID ved hjælp af UserRepository
    public User findById(int id) {
        return userRepository.findById(id); // Kalder findById metoden i UserRepository
    }

    // Denne metode finder en bruger baseret på brugernavn ved hjælp af UserRepository
    public User findByUsername(String username) {
        return userRepository.findByUsername(username); // Kalder findByUsername metoden i UserRepository
    }

    // Denne metode logger en bruger ind ved hjælp af UserRepository
    public User loginUser(String username, String password) {
        User userFromDb = userRepository.findByUsername(username); // Finder brugeren baseret på brugernavn
        if (userFromDb != null && userFromDb.getPassword().equals(password)) { // Tjekker om passwordet matcher
            return userFromDb; // Returnerer brugeren hvis login lykkes
        }
        return null; // Returnerer null hvis login mislykkes
    }
}
