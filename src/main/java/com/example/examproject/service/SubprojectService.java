// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.service'
package com.example.examproject.service;

// Importerer nødvendige klasser fra andre pakker
import com.example.examproject.model.Subproject;
import com.example.examproject.repository.SubprojectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

// Denne linje fortæller, at denne klasse tilbyder tjenester relateret til underprojekter
@Service
public class SubprojectService {

    // Her gemmer vi information om, hvordan vi arbejder med underprojekter
    private final SubprojectRepository subprojectRepository; // Gemmer en reference til SubprojectRepository

    // Dette er en konstruktør, der bruges til at lave en ny SubprojectService med en SubprojectRepository
    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }

    // Denne metode opretter et nyt underprojekt ved hjælp af SubprojectRepository
    public Subproject createSubproject(Subproject subproject) {
        return subprojectRepository.createSubproject(subproject); // Kalder createSubproject metoden i SubprojectRepository
    }

    // Denne metode henter alle underprojekter for et bestemt projekt ved hjælp af SubprojectRepository
    public ArrayList<Subproject> getAllSubprojects(int projectId) {
        return subprojectRepository.getAllSubprojects(projectId); // Kalder getAllSubprojects metoden i SubprojectRepository
    }

    // Denne metode opdaterer et eksisterende underprojekt ved hjælp af SubprojectRepository
    public void updateSubproject(Subproject updateSubproject) {
        subprojectRepository.updateSubproject(updateSubproject); // Kalder updateSubproject metoden i SubprojectRepository
    }

    // Denne metode henter et underprojekt baseret på dets ID ved hjælp af SubprojectRepository
    public Subproject getSubprojectById(int id) {
        return subprojectRepository.getSubprojectById(id); // Kalder getSubprojectById metoden i SubprojectRepository
    }

    // Denne metode sletter et underprojekt ved hjælp af SubprojectRepository
    public void deleteSubproject(int id) {
        subprojectRepository.deleteSubproject(id); // Kalder deleteSubproject metoden i SubprojectRepository
    }
}
