package com.example.examproject.service;

import com.example.examproject.model.Task;
import com.example.examproject.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    // Attribut der holder reference til TaskRepository
    private final TaskRepository taskRepository;

    // Konstruktør der initialiserer TaskService med TaskRepository
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Metode til at oprette en ny opgave ved hjælp af TaskRepository
    public Task createTask(Task task) {
        return taskRepository.createTask(task);
    }

    // Metode til at hente alle opgaver for et specifikt underprojekt ved hjælp af TaskRepository
    public List<Task> getAllTasks(int subProjectId) {
        return taskRepository.getAllTasks(subProjectId);
    }

    // Metode til at opdatere en eksisterende opgave ved hjælp af TaskRepository
    public Task updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    // Metode til at slette en opgave ved hjælp af TaskRepository
    public void deleteTask(int taskId) {
        taskRepository.deleteTask(taskId);
    }

    // Metode til at hente en opgave baseret på dens ID ved hjælp af TaskRepository
    public Task getTaskById(int taskId) {
        return taskRepository.getTaskById(taskId);
    }
}
