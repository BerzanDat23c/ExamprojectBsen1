package com.example.examproject.service;

import com.example.examproject.model.Subproject;
import com.example.examproject.repository.SubprojectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubprojectService {

    // Attribut der holder reference til SubprojectRepository
    private final SubprojectRepository subprojectRepository;

    // Konstruktør der initialiserer SubprojectService med SubprojectRepository
    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }

    // Metode til at oprette et nyt subprojekt ved hjælp af SubprojectRepository
    public Subproject createSubproject(Subproject subproject) {
        return subprojectRepository.createSubproject(subproject);
    }

    // Metode til at hente alle subprojekter for et specifikt projekt ved hjælp af SubprojectRepository
    public ArrayList<Subproject> getAllSubprojects(int projectId) {
        return subprojectRepository.getAllSubprojects(projectId);
    }

    // Metode til at opdatere et eksisterende subprojekt ved hjælp af SubprojectRepository
    public void updateSubproject(Subproject updateSubproject) {
        subprojectRepository.updateSubproject(updateSubproject);
    }

    // Metode til at hente et subprojekt baseret på dets ID ved hjælp af SubprojectRepository
    public Subproject getSubprojectById(int id) {
        return subprojectRepository.getSubprojectById(id);
    }

    // Metode til at slette et subprojekt ved hjælp af SubprojectRepository
    public void deleteSubproject(int id) {
        subprojectRepository.deleteSubproject(id);
    }
}
