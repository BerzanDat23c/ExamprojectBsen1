package com.example.examproject.controller;

import com.example.examproject.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.examproject.service.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserController {

    // Attribut der holder reference til UserService
    private final UserService userService;

    // Konstruktør der initialiserer controlleren med UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Midlertidig bruger (ikke anvendt i koden)
    private User user;

    // Metode til at vise hjemmesiden
    @GetMapping("/")
    public String home() {
        return "index"; // Returnerer viewet for hjemmesiden
    }

    // Metode til at vise registreringsformularen
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userObject", new User()); // Tilføjer et tomt User objekt til modelen
        return "register"; // Returnerer viewet for registrering
    }

    // Metode til at håndtere registreringen af en bruger
    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user); // Kalder service til at registrere brugeren
        return "redirect:/login"; // Redirecter til login-siden
    }

    // Metode til at vise loginformularen
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User()); // Tilføjer et tomt User objekt til modelen
        return "login"; // Returnerer viewet for login
    }

    // Metode til at håndtere login af en bruger
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, Model model) {
        User validatedUser = userService.findbyUsername(user.getUsername()); // Validerer brugernavnet via service
        session.setAttribute("loggedInUser", validatedUser); // Gemmer den validerede bruger i sessionen
        if (validatedUser != null && validatedUser.getPassword().equals(user.getPassword())) { // Tjekker om brugeren er valid og password matcher
            session.setMaxInactiveInterval(1800); // Sætter session timeout til 1800 sekunder (30 minutter)
            return "redirect:/projects"; // Redirecter til projektsiden
        } else {
            model.addAttribute("error", "Invalid username or password"); // Tilføjer fejlbesked til modelen
            return "login"; // Returnerer til login-siden ved fejl
        }
    }

    // Metode til at håndtere logout af en bruger
    @GetMapping("/logud")
    public String logout(HttpSession session) {
        session.invalidate(); // Invaliderer sessionen (logger brugeren ud)
        return "index"; // Redirecter til hjemmesiden
    }
}
