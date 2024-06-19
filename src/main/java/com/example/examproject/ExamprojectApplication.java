// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject'
package com.example.examproject;

// Importerer nødvendige klasser fra Spring biblioteket
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Denne linje fortæller, at denne klasse er en Spring Boot-applikation
@SpringBootApplication
public class ExamprojectApplication {

    // Denne metode er hovedmetoden, som starter applikationen
    public static void main(String[] args) {
        // SpringApplication.run starter applikationen
        SpringApplication.run(ExamprojectApplication.class, args);
    }
}
