package com.example.examproject.controller;

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

@Controller
public class ProjectController {

    // Attributter der holder reference til service-klasserne
    private SubprojectService subprojectService;
    private TaskService taskService;
    private ProjectService projectService;

    // Konstruktør, der initialiserer controlleren med de nødvendige services
    public ProjectController(ProjectService projectService, SubprojectService subprojectService, TaskService taskService) {
        this.projectService = projectService;
        this.subprojectService = subprojectService;
        this.taskService = taskService;
    }

    // Metode til at vise formularen for at oprette et projekt
    @GetMapping("/create_project")
    public String createProjectform(Model model) {
        model.addAttribute("projectObject", new Project());
        return "create_project";
    }

    // Metode til at håndtere formularens submission og oprette et projekt
    @PostMapping("/create_project")
    public String createProject(@ModelAttribute Project project, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser"); // Henter den loggede bruger fra sessionen
        project.setUsers_id(user.getid()); // Sætter brugerens ID på projektet
        projectService.createProject(project); // Kalder service til at oprette projektet
        return "redirect:/projects"; // Redirecter til listen af projekter
    }

    // Metode til at vise projektets forside
    @GetMapping("/project_frontpage")
    public String projectFrontpage(Model model) {
        model.addAttribute("projectObject", new Project());
        return "project_frontpage";
    }

    // Metode til at hente og vise alle projekter for den loggede bruger
    @GetMapping("/projects")
    public String getAllProjects(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser"); // Henter den loggede bruger fra sessionen
        ArrayList<Project> projects = projectService.getAllProjects(user.getid()); // Henter brugerens projekter
        model.addAttribute("projects", projects); // Tilføjer projekterne til modelen
        return "project_frontpage";
    }

    // Metode til at vise formularen for at redigere et projekt
    @GetMapping("/edit_project/{id}")
    public String showEditProjectForm(@PathVariable("id") int id, Model model) {
        Project project = projectService.getProjectById(id); // Henter projektet ud fra ID
        model.addAttribute("project", project); // Tilføjer projektet til modelen
        return "edit_project";
    }

    // Metode til at håndtere opdateringen af et projekt
    @PostMapping("/edit_project/{id}")
    public String updateProject(Project project) {
        projectService.updateProject(project); // Opdaterer projektet via service
        return "redirect:/projects"; // Redirecter til listen af projekter
    }

    // Metode til at vise bekræftelsessiden for sletning af projekt
    @GetMapping("/confirm_delete/{projectId}")
    public String confirmDelete(@PathVariable("projectId") int projectId, Model model) {
        model.addAttribute("projectId", projectId); // Tilføjer projektID til modelen
        return "confirm_delete";
    }

    // Metode til at håndtere sletning af projekt
    @PostMapping("/deleteProject")
    public String deleteProject(@RequestParam("projectId") int projectId, Model model) {
        model.addAttribute("projectId", projectId); // Tilføjer projektID til modelen
        projectService.deleteProject(projectId); // Sletter projektet via service
        return "redirect:/projects"; // Redirecter til listen af projekter
    }

    // Metode til at vise detaljer for et specifikt projekt
    @GetMapping("/project/{id}")
    public String viewProjectDetails(@PathVariable("id") int id, Model model, HttpSession session) {
        Project project = projectService.getProjectById(id); // Henter projektet ud fra ID
        List<Subproject> subprojects = subprojectService.getAllSubprojects(id); // Henter alle underprojekter for projektet
        Task task = taskService.getTaskById(id); // Henter opgaven ud fra ID
        session.setAttribute("currentProjectId", id); // Sætter det aktuelle projektID i sessionen
        model.addAttribute("project", project); // Tilføjer projektet til modelen
        model.addAttribute("subprojects", subprojects); // Tilføjer underprojekterne til modelen
        model.addAttribute("task", task); // Tilføjer opgaven til modelen
        return "subprojects"; // Returnerer viewet for underprojekter
    }
}
