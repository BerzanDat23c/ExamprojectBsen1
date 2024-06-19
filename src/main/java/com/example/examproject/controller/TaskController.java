// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.controller'
package com.example.examproject.controller;

// Her importerer vi forskellige klasser, som vi skal bruge i vores program
import com.example.examproject.model.Task;
import com.example.examproject.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Denne linje fortæller, at denne klasse er en Controller, som håndterer brugerens anmodninger
@Controller
public class TaskController {

    // Her laver vi en variabel, som vi bruger til at arbejde med opgaver
    private final TaskService taskService;

    // Dette er en 'konstruktør', der sætter vores variabel op, så vi kan bruge den senere
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Denne metode viser en side, hvor man kan lave en ny opgave
    @GetMapping("/create_task/{subprojectId}")
    public String createTaskForm(@PathVariable("subprojectId") int subprojectId, Model model, HttpSession session) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        model.addAttribute("taskObject", new Task()); // Tilføjer en tom opgave, så vi kan udfylde den på siden
        session.setAttribute("subprojectId", subprojectId); // Gemmer det aktuelle underprojektID i sessionen
        return "create_task"; // Viser siden kaldet 'create_task.html'
    }

    // Denne metode håndterer det, når vi sender formularen for at lave en ny opgave
    @PostMapping("/create_task")
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        // @ModelAttribute tager data fra formularen (som brugeren har udfyldt)
        Integer subprojectId = (Integer) session.getAttribute("subprojectId"); // Finder ud af, hvilket underprojekt der er aktuelt
        task.setSubProject_Id(subprojectId); // Sætter underprojektID på opgaven
        taskService.createTask(task); // Gemmer den nye opgave
        return "redirect:/tasks/" + subprojectId; // Sender os tilbage til siden med opgaverne
    }

    // Denne metode henter og viser alle opgaver for et specifikt underprojekt
    @GetMapping("/tasks/{subprojectId}")
    public String getAllTasks(@PathVariable("subprojectId") int subprojectId, Model model) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        List<Task> tasks = taskService.getAllTasks(subprojectId); // Henter alle opgaver for underprojektet
        model.addAttribute("tasks", tasks); // Tilføjer opgaverne, så vi kan vise dem på siden
        model.addAttribute("subprojectId", subprojectId); // Tilføjer underprojektID til modellen
        return "tasks"; // Viser siden kaldet 'tasks.html'
    }

    // Denne metode viser en side, hvor man kan redigere en opgave
    @GetMapping("/edit_task/{id}")
    public String showEditTaskForm(@PathVariable("id") int id, Model model) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        Task task = taskService.getTaskById(id); // Henter opgaven, vi vil redigere, ved hjælp af ID
        model.addAttribute("task", task); // Tilføjer opgaven, så vi kan vise den på siden
        return "edit_task"; // Viser siden kaldet 'edit_task.html'
    }

    // Denne metode håndterer opdateringen af en opgave
    @PostMapping("/edit_task/{subprojectId}")
    public String updateTask(@PathVariable("subprojectId") int subprojectId, Model model, @ModelAttribute Task task) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        model.addAttribute("subprojectId", subprojectId); // Tilføjer underprojektID til modellen
        taskService.updateTask(task); // Opdaterer opgaven med nye informationer
        return "redirect:/tasks/" + subprojectId; // Sender os tilbage til siden med opgaverne
    }

    // Denne metode viser en side, hvor vi kan bekræfte, om vi vil slette en opgave
    @GetMapping("/confirm_delete_task/{id}/{subprojectId}")
    public String confirmDelete(@PathVariable("id") int taskId, @PathVariable("subprojectId") int subprojectId, Model model) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        model.addAttribute("taskId", taskId); // Tilføjer opgaveID til modellen
        model.addAttribute("subprojectId", subprojectId); // Tilføjer underprojektID til modellen
        return "confirm_delete_task"; // Viser siden kaldet 'confirm_delete_task.html'
    }

    // Denne metode håndterer sletningen af en opgave
    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskId") int taskId, @RequestParam("subprojectId") int subprojectId) {
        // @RequestParam tager parametre fra URL'en eller formularen og bruger dem i koden
        taskService.deleteTask(taskId); // Sletter opgaven
        return "redirect:/tasks/" + subprojectId; // Sender os tilbage til siden med opgaverne
    }
}
