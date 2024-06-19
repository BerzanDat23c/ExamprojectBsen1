// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.controller'
package com.example.examproject.controller;

// Her importerer vi forskellige klasser, som vi skal bruge i vores program
import com.example.examproject.model.Subproject;
import com.example.examproject.service.SubprojectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Denne linje fortæller, at denne klasse er en Controller, som håndterer brugerens anmodninger
@Controller
public class SubprojectController {

    // Her laver vi en variabel, som vi bruger til at arbejde med underprojekter
    private SubprojectService subprojectService;

    // Dette er en 'konstruktør', der sætter vores variabel op, så vi kan bruge den senere
    public SubprojectController(SubprojectService subprojectService) {
        this.subprojectService = subprojectService;
    }

    // Denne metode viser en side, hvor man kan lave et nyt underprojekt
    @GetMapping("/create_subproject/{projectId}")
    public String createSubprojectForm(@PathVariable("projectId") int projectId, Model model, HttpSession session) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        model.addAttribute("subprojectObject", new Subproject()); // Tilføjer et tomt underprojekt, så vi kan udfylde det på siden
        session.setAttribute("currentProjectId", projectId); // Gemmer det aktuelle projektID i sessionen
        return "create_subproject"; // Viser siden kaldet 'create_subproject.html'
    }

    // Denne metode håndterer det, når vi sender formularen for at lave et nyt underprojekt
    @PostMapping("/create_subproject")
    public String createSubproject(@ModelAttribute Subproject subproject, HttpSession session) {
        // @ModelAttribute tager data fra formularen (som brugeren har udfyldt)
        Integer projectId = (Integer) session.getAttribute("currentProjectId"); // Finder ud af, hvilket projekt der er aktuelt
        subproject.setProjectId(projectId); // Sætter projektID på underprojektet
        subprojectService.createSubproject(subproject); // Gemmer det nye underprojekt
        return "redirect:/project/" + projectId; // Sender os tilbage til projektets side
    }

    // Denne metode henter og viser alle underprojekter for et specifikt projekt
    @GetMapping("/subprojects/{projectId}")
    public String getAllSubprojects(@PathVariable("projectId") int projectId, Model model, HttpSession session) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        model.addAttribute("id", projectId); // Tilføjer projektID til modellen
        session.setAttribute("currentProjectId", projectId); // Gemmer det aktuelle projektID i sessionen
        List<Subproject> subprojects = subprojectService.getAllSubprojects(projectId); // Henter alle underprojekter for projektet
        model.addAttribute("subprojects", subprojects); // Tilføjer underprojekterne, så vi kan vise dem på siden
        return "subprojects"; // Viser siden kaldet 'subprojects.html'
    }

    // Denne metode viser en side, hvor man kan redigere et underprojekt
    @GetMapping("/edit_subproject/{id}")
    public String showEditSubprojectForm(@PathVariable("id") int id, Model model) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        Subproject subproject = subprojectService.getSubprojectById(id); // Henter underprojektet, vi vil redigere, ved hjælp af ID
        model.addAttribute("subproject", subproject); // Tilføjer underprojektet, så vi kan vise det på siden
        return "edit_subproject"; // Viser siden kaldet 'edit_subproject.html'
    }

    // Denne metode håndterer opdateringen af et underprojekt
    @PostMapping("/edit_subproject/{id}")
    public String updateSubproject(@PathVariable("id") int id, Model model, Subproject subproject) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        model.addAttribute("id", id); // Tilføjer underprojektets ID til modellen
        subprojectService.updateSubproject(subproject); // Opdaterer underprojektet med nye informationer
        return "redirect:/project/" + subproject.getProject_Id(); // Sender os tilbage til projektets side
    }

    // Denne metode viser en side, hvor vi kan bekræfte, om vi vil slette et underprojekt
    @GetMapping("/confirm_delete_subproject/{subprojectId}/{projectId}")
    public String confirmDeleteSubproject(@PathVariable("subprojectId") int subprojectId, @PathVariable("projectId") int projectId, Model model) {
        // @PathVariable tager et tal fra web-adressen (URL'en) og bruger det i koden
        Subproject subproject = subprojectService.getSubprojectById(subprojectId); // Henter underprojektet ved hjælp af ID
        model.addAttribute("subprojectId", subprojectId); // Tilføjer underprojektets ID til modellen
        model.addAttribute("projectId", projectId); // Tilføjer projektets ID til modellen
        return "confirm_delete_subproject"; // Viser siden kaldet 'confirm_delete_subproject.html'
    }

    // Denne metode håndterer sletningen af et underprojekt
    @PostMapping("/deleteSubproject")
    public String deleteSubproject(@RequestParam("subprojectId") int subprojectId, @RequestParam("projectId") int projectId) {
        subprojectService.deleteSubproject(subprojectId); // Sletter underprojektet
        return "redirect:/project/" + projectId; // Sender os tilbage til projektets side
    }
}
