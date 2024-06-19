// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.model'
package com.example.examproject.model;

// Importerer nødvendige klasser fra Java-biblioteket
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

// Definerer en klasse kaldet 'Subproject'
public class Subproject {

    // Variabler der holder information om underprojektet
    private int id; // Underprojektets ID (en unik identifikator)
    private int project_Id; // ID for projektet, som underprojektet hører til
    private String name; // Underprojektets navn
    private String description; // Beskrivelse af underprojektet
    private String status; // Status for underprojektet (f.eks. 'In Progress', 'Completed')

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Angiver datoformatet
    private LocalDate startDate; // Startdato for underprojektet
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Angiver datoformatet
    private LocalDate endDate; // Slutdato for underprojektet

    // Dette er en 'konstruktør', der bruges til at lave et nyt underprojekt med alle informationerne
    public Subproject(String name, String description, String status, LocalDate startDate, LocalDate endDate, int id, int project_Id) {
        this.name = name; // Sætter underprojektets navn
        this.description = description; // Sætter underprojektets beskrivelse
        this.status = status; // Sætter underprojektets status
        this.startDate = startDate; // Sætter underprojektets startdato
        this.endDate = endDate; // Sætter underprojektets slutdato
        this.id = id; // Sætter underprojektets ID
        this.project_Id = project_Id; // Sætter ID for projektet, som underprojektet hører til
    }

    // Dette er en standard 'konstruktør', der ikke tager nogen parametre
    public Subproject() {
    }

    // Gettere og settere - disse bruges til at få og sætte værdierne for variablerne
    public int getId() {
        return id; // Returnerer underprojektets ID
    }

    public int getProject_Id() {
        return project_Id; // Returnerer projektets ID, som underprojektet hører til
    }

    public String getName() {
        return name; // Returnerer underprojektets navn
    }

    public String getDescription() {
        return description; // Returnerer underprojektets beskrivelse
    }

    public String getStatus() {
        return status; // Returnerer underprojektets status
    }

    public LocalDate getStartDate() {
        return startDate; // Returnerer underprojektets startdato
    }

    public LocalDate getEndDate() {
        return endDate; // Returnerer underprojektets slutdato
    }

    public void setId(int id) {
        this.id = id; // Sætter underprojektets ID
    }

    public void setProjectId(int project_Id) {
        this.project_Id = project_Id; // Sætter projektets ID, som underprojektet hører til
    }

    public void setName(String name) {
        this.name = name; // Sætter underprojektets navn
    }

    public void setDescription(String description) {
        this.description = description; // Sætter underprojektets beskrivelse
    }

    public void setStatus(String status) {
        this.status = status; // Sætter underprojektets status
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate; // Sætter underprojektets startdato
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate; // Sætter underprojektets slutdato
    }

    // Overrider toString metoden til at returnere en tekstbeskrivelse af underprojektet
    @Override
    public String toString() {
        return "Subproject{" +
                "id=" + id +
                ", project_Id=" + project_Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
