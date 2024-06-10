package com.example.examproject.controller;

import com.example.examproject.model.Subproject;
import com.example.examproject.service.SubprojectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SubprojectController {

    // Attribut der holder reference til subprojectService
    private SubprojectService subprojectService;

    // Konstruktør der initialiserer controlleren med subprojectService
    public SubprojectController(SubprojectService subprojectService) {
        this.subprojectService = subprojectService;
    }

    // Metode til at vise formularen for at oprette et underprojekt
    @GetMapping("/create_subproject/{projectId}")
    public String createSubprojectForm(@PathVariable("projectId") int projectId, Model model, HttpSession session) {
        model.addAttribute("subprojectObject", new Subproject()); // Tilføjer et tomt Subproject objekt til modelen
        session.setAttribute("currentProjectId", projectId); // Gemmer det aktuelle projektID i sessionen
        return "create_subproject"; // Returnerer viewet for at oprette underprojekt
    }

    // Metode til at håndtere oprettelsen af et underprojekt
    @PostMapping("/create_subproject")
    public String createSubproject(@ModelAttribute Subproject subproject, HttpSession session) {
        Integer projectId = (Integer) session.getAttribute("currentProjectId"); // Henter det aktuelle projektID fra sessionen
        subproject.setProjectId(projectId); // Sætter projektID på underprojektet
        subprojectService.createSubproject(subproject); // Kalder service til at oprette underprojektet
        return "redirect:/project/" + projectId; // Redirecter til projektets side
    }

    // Metode til at hente og vise alle underprojekter for et specifikt projekt
    @GetMapping("/subprojects/{projectId}")
    public String getAllSubprojects(@PathVariable("projectId") int projectId, Model model, HttpSession session) {
        model.addAttribute("id", projectId); // Tilføjer projektID til modelen
        session.setAttribute("currentProjectId", projectId); // Gemmer det aktuelle projektID i sessionen
        List<Subproject> subprojects = subprojectService.getAllSubprojects(projectId); // Henter alle underprojekter for projektet
        model.addAttribute("subprojects", subprojects); // Tilføjer underprojekterne til modelen
        return "subprojects"; // Returnerer viewet for underprojekter
    }

    // Metode til at vise formularen for at redigere et underprojekt
    @GetMapping("/edit_subproject/{id}")
    public String showEditSubprojectForm(@PathVariable("id") int id, Model model) {
        Subproject subproject = subprojectService.getSubprojectById(id); // Henter underprojektet ud fra ID
        model.addAttribute("subproject", subproject); // Tilføjer underprojektet til modelen
        return "edit_subproject"; // Returnerer viewet for at redigere underprojekt
    }

    // Metode til at håndtere opdateringen af et underprojekt
    @PostMapping("/edit_subproject/{id}")
    public String updateSubproject(@PathVariable("id") int id, Model model, Subproject subproject) {
        model.addAttribute("id", id); // Tilføjer underprojektets ID til modelen
        subprojectService.updateSubproject(subproject); // Opdaterer underprojektet via service
        return "redirect:/project/" + subproject.getProject_Id(); // Redirecter til projektets side
    }

    // Metode til at vise bekræftelsessiden for sletning af et underprojekt
    @GetMapping("/confirm_delete_subproject/{subprojectId}/{projectId}")
    public String confirmDeleteSubproject(@PathVariable("subprojectId") int subprojectId, @PathVariable("projectId") int projectId, Model model) {
        Subproject subproject = subprojectService.getSubprojectById(subprojectId); // Henter underprojektet ud fra ID
        model.addAttribute("subprojectId", subprojectId); // Tilføjer underprojektets ID til modelen
        model.addAttribute("projectId", projectId); // Tilføjer projektets ID til modelen
        return "confirm_delete_subproject"; // Returnerer viewet for at bekræfte sletning af underprojekt
    }

    // Metode til at håndtere sletningen af et underprojekt
    @PostMapping("/deleteSubproject")
    public String deleteSubproject(@RequestParam("subprojectId") int subprojectId, @RequestParam("projectId") int projectId) {
        subprojectService.deleteSubproject(subprojectId); // Sletter underprojektet via service
        return "redirect:/project/" + projectId; // Redirecter til projektets side
    }
}
