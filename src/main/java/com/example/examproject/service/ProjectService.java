package com.example.examproject.service;

import com.example.examproject.model.Project;
import com.example.examproject.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectService {

    // Attribut der holder reference til ProjectRepository
    private final ProjectRepository projectRepository;

    // Konstruktør der initialiserer ProjectService med ProjectRepository
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Metode til at oprette et nyt projekt ved hjælp af ProjectRepository
    public Project createProject(Project project) {
        return projectRepository.createProject(project);
    }

    // Metode til at hente alle projekter for en specifik bruger ved hjælp af ProjectRepository
    public ArrayList<Project> getAllProjects(int userId) {
        return projectRepository.getAllProjects(userId);
    }

    // Metode til at opdatere et eksisterende projekt ved hjælp af ProjectRepository
    public void updateProject(Project updateProject) {
        projectRepository.updateProject(updateProject);
    }

    // Metode til at hente et projekt baseret på dets ID ved hjælp af ProjectRepository
    public Project getProjectById(int id) {
        return projectRepository.getProjectById(id);
    }

    // Metode til at slette et projekt ved hjælp af ProjectRepository
    public void deleteProject(int id) {
        projectRepository.deleteProject(id);
    }
}
