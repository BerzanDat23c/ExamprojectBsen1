// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.controller'
package com.example.examproject.controller;

// Her importerer vi forskellige klasser, som vi skal bruge i vores program
import com.example.examproject.model.Project;
import com.example.examproject.model.Subproject;
import com.example.examproject.model.Task;
import com.example.examproject.model.User;
import com.example.examproject.service.ProjectService;
import com.example.examproject.service.SubprojectService;
import com.example.examproject.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// Denne linje fortæller, at denne klasse er en Controller, som håndterer brugerens anmodninger
@Controller
public class ProjectController {

    // Her laver vi tre variabler, som vi bruger til at arbejde med projekter, underprojekter og opgaver
    private final SubprojectService subprojectService;
    private final TaskService taskService;
    private final ProjectService projectService;

    // Dette er en 'konstruktør', der sætter vores variabler op, så vi kan bruge dem senere
    public ProjectController(ProjectService projectService, SubprojectService subprojectService, TaskService taskService) {
        this.projectService = projectService;
        this.subprojectService = subprojectService;
        this.taskService = taskService;
    }

    // Denne metode viser en side, hvor man kan lave et nyt projekt
    @GetMapping("/create_project")
    public String createProjectform(Model model) {
        model.addAttribute("projectObject", new Project()); // Tilføjer et tomt projekt, så vi kan udfylde det på siden
        return "create_project"; // Viser siden kaldet 'create_project.html'
    }

    // Denne metode håndterer det, når vi sender formularen for at lave et nyt projekt
    @PostMapping("/create_project")
    public String createProject(@ModelAttribute Project project, HttpSession session) {
        // @ModelAttribute tager data fra formularen (som brugeren har udfyldt)
        User user = (User) session.getAttribute("loggedInUser"); // Finder ud af, hvilken bruger der er logget ind
        project.setUsers_id(user.getid()); // Sætter brugerens ID på projektet
        projectService.createProject(project); // Gemmer det nye projekt
        return "redirect:/projects"; // Sender os tilbage til siden med alle projekterne
    }

    // Denne metode viser forsiden for et projekt
    @GetMapping("/project_frontpage")
    public String projectFrontpage(Model model) {
        model.addAttribute("projectObject", new Project()); // Tilføjer et tomt projekt, som vi kan vise på forsiden
        return "project_frontpage"; // Viser siden kaldet 'project_frontpage.html'
    }

    // Denne metode henter og viser alle projekter for den bruger, der er logget ind
    @GetMapping("/projects")
    public String getAllProjects(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser"); // Finder ud af, hvilken bruger der er logget ind
        ArrayList<Project> projects = projectService.getAllProjects(user.getid()); // Henter alle brugerens projekter
        model.addAttribute("projects", projects); // Tilføjer projekterne, så vi kan vise dem på siden
        return "project_frontpage"; // Viser siden kaldet 'project_frontpage.html'
    }

    // Denne metode viser en side, hvor man kan redigere et projekt
    @GetMapping("/edit_project/{id}")
    public String showEditProjectForm(@PathVariable("id") int id, Model model) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        Project project = projectService.getProjectById(id); // Henter projektet, vi vil redigere, ved hjælp af ID
        model.addAttribute("project", project); // Tilføjer projektet, så vi kan vise det på siden
        return "edit_project"; // Viser siden kaldet 'edit_project.html'
    }

    // Denne metode håndterer opdateringen af et projekt
    @PostMapping("/edit_project/{id}")
    public String updateProject(Project project) {
        projectService.updateProject(project); // Opdaterer projektet med nye informationer
        return "redirect:/projects"; // Sender os tilbage til siden med alle projekterne
    }

    // Denne metode viser en side, hvor vi kan bekræfte, om vi vil slette et projekt
    @GetMapping("/confirm_delete/{projectId}")
    public String confirmDelete(@PathVariable("projectId") int projectId, Model model) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        model.addAttribute("projectId", projectId); // Tilføjer projektets ID, så vi ved hvilket projekt, der skal slettes
        return "confirm_delete"; // Viser siden kaldet 'confirm_delete.html'
    }

    // Denne metode håndterer sletningen af et projekt
    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("projectId") int projectId, Model model) {
        // @RequestParam tager et parameter fra URL'en eller formularen og bruger det i koden
        model.addAttribute("projectId", projectId); // Tilføjer projektets ID, så vi ved hvilket projekt, der skal slettes
        projectService.deleteProject(projectId); // Sletter projektet
        return "redirect:/projects"; // Sender os tilbage til siden med alle projekterne
    }

    // Denne metode viser detaljer om et bestemt projekt
    @GetMapping("/project/{id}")
    public String viewProjectDetails(@PathVariable("id") int id, Model model, HttpSession session) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        Project project = projectService.getProjectById(id); // Henter projektet ved hjælp af ID
        List<Subproject> subprojects = subprojectService.getAllSubprojects(id); // Henter alle underprojekter for projektet
        Task task = taskService.getTaskById(id); // Henter opgaven ved hjælp af ID
        session.setAttribute("currentProjectId", id); // Sætter det aktuelle projektID i sessionen
        model.addAttribute("project", project); // Tilføjer projektet, så vi kan vise det på siden
        model.addAttribute("subprojects", subprojects); // Tilføjer underprojekterne, så vi kan vise dem på siden
        model.addAttribute("task", task); // Tilføjer opgaven, så vi kan vise den på siden
        return "subprojects"; // Viser siden kaldet 'subprojects.html'
    }
}
