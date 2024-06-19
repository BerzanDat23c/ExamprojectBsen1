// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.service'
package com.example.examproject.service;

// Importerer nødvendige klasser fra andre pakker
import com.example.examproject.model.Task;
import com.example.examproject.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

// Denne linje fortæller, at denne klasse tilbyder tjenester relateret til opgaver
@Service
public class TaskService {

    // Her gemmer vi information om, hvordan vi arbejder med opgaver
    private final TaskRepository taskRepository; // Gemmer en reference til TaskRepository

    // Dette er en konstruktør, der bruges til at lave en ny TaskService med en TaskRepository
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Denne metode opretter en ny opgave ved hjælp af TaskRepository
    public Task createTask(Task task) {
        return taskRepository.createTask(task); // Kalder createTask metoden i TaskRepository
    }

    // Denne metode henter alle opgaver for et bestemt underprojekt ved hjælp af TaskRepository
    public List<Task> getAllTasks(int subProjectId) {
        return taskRepository.getAllTasks(subProjectId); // Kalder getAllTasks metoden i TaskRepository
    }

    // Denne metode opdaterer en eksisterende opgave ved hjælp af TaskRepository
    public Task updateTask(Task task) {
        return taskRepository.updateTask(task); // Kalder updateTask metoden i TaskRepository
    }

    // Denne metode sletter en opgave ved hjælp af TaskRepository
    public void deleteTask(int taskId) {
        taskRepository.deleteTask(taskId); // Kalder deleteTask metoden i TaskRepository
    }

    // Denne metode henter en opgave baseret på dens ID ved hjælp af TaskRepository
    public Task getTaskById(int taskId) {
        return taskRepository.getTaskById(taskId); // Kalder getTaskById metoden i TaskRepository
    }
}
