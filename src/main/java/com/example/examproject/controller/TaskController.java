package com.example.examproject.controller;

import com.example.examproject.model.Task;
import com.example.examproject.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    // Attribut der holder reference til taskService
    private final TaskService taskService;

    // Konstruktør der initialiserer controlleren med taskService
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Metode til at vise formularen for at oprette en opgave
    @GetMapping("/create_task/{subprojectId}")
    public String createTaskForm(@PathVariable("subprojectId") int subprojectId, Model model, HttpSession session) {
        model.addAttribute("taskObject", new Task()); // Tilføjer et tomt Task objekt til modelen
        session.setAttribute("subprojectId", subprojectId); // Gemmer det aktuelle underprojektID i sessionen
        return "create_task"; // Returnerer viewet for at oprette opgave
    }

    // Metode til at håndtere oprettelsen af en opgave
    @PostMapping("/create_task")
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        Integer subprojectId = (Integer) session.getAttribute("subprojectId"); // Henter det aktuelle underprojektID fra sessionen
        task.setSubProject_Id(subprojectId); // Sætter underprojektID på opgaven
        taskService.createTask(task); // Kalder service til at oprette opgaven
        return "redirect:/tasks/" + subprojectId; // Redirecter til opgavens side
    }

    // Metode til at hente og vise alle opgaver for et specifikt underprojekt
    @GetMapping("/tasks/{subprojectId}")
    public String getAllTasks(@PathVariable("subprojectId") int subprojectId, Model model) {
        List<Task> tasks = taskService.getAllTasks(subprojectId); // Henter alle opgaver for underprojektet
        model.addAttribute("tasks", tasks); // Tilføjer opgaverne til modelen
        model.addAttribute("subprojectId", subprojectId); // Tilføjer underprojektID til modelen
        return "tasks"; // Returnerer viewet for opgaver
    }

    // Metode til at vise formularen for at redigere en opgave
    @GetMapping("/edit_task/{id}")
    public String showEditTaskForm(@PathVariable("id") int id, Model model) {
        Task task = taskService.getTaskById(id); // Henter opgaven ud fra ID
        model.addAttribute("task", task); // Tilføjer opgaven til modelen
        return "edit_task"; // Returnerer viewet for at redigere opgave
    }

    // Metode til at håndtere opdateringen af en opgave
    @PostMapping("/edit_task/{subprojectId}")
    public String updateTask(@PathVariable("subprojectId") int subprojectId, Model model, @ModelAttribute Task task) {
        model.addAttribute("subprojectId", subprojectId); // Tilføjer underprojektID til modelen
        taskService.updateTask(task); // Opdaterer opgaven via service
        return "redirect:/tasks/" + subprojectId; // Redirecter til opgavens side
    }

    // Metode til at vise bekræftelsessiden for sletning af en opgave
    @GetMapping("/confirm_delete_task/{id}/{subprojectId}")
    public String confirmDelete(@PathVariable("id") int taskId, @PathVariable("subprojectId") int subprojectId, Model model) {
        model.addAttribute("taskId", taskId); // Tilføjer opgaveID til modelen
        model.addAttribute("subprojectId", subprojectId); // Tilføjer underprojektID til modelen
        return "confirm_delete_task"; // Returnerer viewet for at bekræfte sletning af opgave
    }

    // Metode til at håndtere sletningen af en opgave
    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskId") int taskId, @RequestParam("subprojectId") int subprojectId) {
        taskService.deleteTask(taskId); // Sletter opgaven via service
        return "redirect:/tasks/" + subprojectId; // Redirecter til opgavens side
    }
}
