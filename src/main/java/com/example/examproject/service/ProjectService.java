// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.service'
package com.example.examproject.service;

// Importerer nødvendige klasser fra andre pakker
import com.example.examproject.model.Project;
import com.example.examproject.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

// Denne linje fortæller, at denne klasse tilbyder tjenester relateret til projekter
@Service
public class ProjectService {

    // Her gemmer vi information om, hvordan vi arbejder med projektdata
    private final ProjectRepository projectRepository; // Gemmer en reference til ProjectRepository

    // Dette er en konstruktør, der bruges til at lave en ny ProjectService med en ProjectRepository
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Denne metode opretter et nyt projekt ved hjælp af ProjectRepository
    public Project createProject(Project project) {
        return projectRepository.createProject(project); // Kalder createProject metoden i ProjectRepository
    }

    // Denne metode henter alle projekter for en bestemt bruger ved hjælp af ProjectRepository
    public ArrayList<Project> getAllProjects(int userId) {
        return projectRepository.getAllProjects(userId); // Kalder getAllProjects metoden i ProjectRepository
    }

    // Denne metode opdaterer et eksisterende projekt ved hjælp af ProjectRepository
    public void updateProject(Project updateProject) {
        projectRepository.updateProject(updateProject); // Kalder updateProject metoden i ProjectRepository
    }

    // Denne metode henter et projekt baseret på dets ID ved hjælp af ProjectRepository
    public Project getProjectById(int id) {
        return projectRepository.getProjectById(id); // Kalder getProjectById metoden i ProjectRepository
    }

    // Denne metode sletter et projekt ved hjælp af ProjectRepository
    public void deleteProject(int id) {
        projectRepository.deleteProject(id); // Kalder deleteProject metoden i ProjectRepository
    }
}
