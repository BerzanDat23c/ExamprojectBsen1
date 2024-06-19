// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.controller'
package com.example.examproject.controller;

// Her importerer vi forskellige klasser, som vi skal bruge i vores program
import com.example.examproject.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.examproject.service.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

// Denne linje fortæller, at denne klasse er en Controller, som håndterer brugerens anmodninger
@Controller
public class UserController {

    // Her laver vi en variabel, som vi bruger til at arbejde med brugere
    private final UserService userService;

    // Dette er en 'konstruktør', der sætter vores variabel op, så vi kan bruge den senere
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Midlertidig bruger (ikke anvendt i koden)
    private User user;

    // Denne metode viser hjemmesiden
    @GetMapping("/")
    public String home() {
        return "index"; // Viser siden kaldet 'index.html'
    }

    // Denne metode viser en side, hvor man kan registrere en ny bruger
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userObject", new User()); // Tilføjer en tom bruger, så vi kan udfylde den på siden
        return "register"; // Viser siden kaldet 'register.html'
    }

    // Denne metode håndterer det, når vi sender formularen for at registrere en ny bruger
    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute User user) {
        // @ModelAttribute tager data fra formularen (som brugeren har udfyldt)
        userService.registerUser(user); // Gemmer den nye bruger
        return "redirect:/login"; // Sender os til login-siden
    }

    // Denne metode viser en side, hvor man kan logge ind
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User()); // Tilføjer en tom bruger, så vi kan udfylde den på siden
        return "login"; // Viser siden kaldet 'login.html'
    }

    // Denne metode håndterer det, når vi sender formularen for at logge ind
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, Model model) {
        // @ModelAttribute tager data fra formularen (som brugeren har udfyldt)
        User validatedUser = userService.findByUsername(user.getUsername()); // Finder brugeren i systemet ved hjælp af brugernavnet
        session.setAttribute("loggedInUser", validatedUser); // Gemmer den fundne bruger i sessionen
        if (validatedUser != null && validatedUser.getPassword().equals(user.getPassword())) { // Tjekker om brugeren findes og om passwordet er korrekt
            session.setMaxInactiveInterval(1800); // Sætter session timeout til 1800 sekunder (30 minutter)
            return "redirect:/projects"; // Sender os til siden med projekter
        } else {
            model.addAttribute("error", "Invalid username or password"); // Tilføjer en fejlbesked, hvis brugernavnet eller passwordet er forkert
            return "login"; // Viser login-siden igen, hvis der er en fejl
        }
    }

    // Denne metode håndterer det, når vi logger ud
    @GetMapping("/logud")
    public String logout(HttpSession session) {
        session.invalidate(); // Logger brugeren ud ved at slette sessionen
        return "index"; // Sender os til hjemmesiden
    }
}
