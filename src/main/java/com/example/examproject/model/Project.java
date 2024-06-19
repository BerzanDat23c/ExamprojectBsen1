// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.model'
package com.example.examproject.model;

// Importerer nødvendige klasser fra Java-biblioteket
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

// Definerer en klasse kaldet 'Project'
public class Project {

    // Variabler der holder information om projektet
    private int id; // Projektets ID (en unik identifikator)
    private int users_id; // ID for brugeren, der oprettede projektet
    private String name; // Projektets navn
    private String description; // Beskrivelse af projektet
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Angiver datoformatet
    private LocalDate startDate; // Startdato for projektet
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Angiver datoformatet
    private LocalDate endDate; // Slutdato for projektet
    private String status; // Status for projektet (f.eks. 'In Progress', 'Completed')

    // Dette er en 'konstruktør', der bruges til at lave et nyt projekt med alle informationerne
    public Project(int id, int users_id, String name, String description, LocalDate startDate, LocalDate endDate, String status) {
        this.id = id; // Sætter projektets ID
        this.users_id = users_id; // Sætter brugerens ID
        this.name = name; // Sætter projektets navn
        this.description = description; // Sætter projektets beskrivelse
        this.startDate = startDate; // Sætter projektets startdato
        this.endDate = endDate; // Sætter projektets slutdato
        this.status = status; // Sætter projektets status
    }

    // Dette er en standard 'konstruktør', der ikke tager nogen parametre
    public Project() {
    }

    // Gettere og settere - disse bruges til at få og sætte værdierne for variablerne
    public int getId() {
        return id; // Returnerer projektets ID
    }

    public int getUsers_id() {
        return users_id; // Returnerer brugerens ID
    }

    public String getName() {
        return name; // Returnerer projektets navn
    }

    public String getDescription() {
        return description; // Returnerer projektets beskrivelse
    }

    public LocalDate getStartDate() {
        return startDate; // Returnerer projektets startdato
    }

    public LocalDate getEndDate() {
        return endDate; // Returnerer projektets slutdato
    }

    public String getStatus() {
        return status; // Returnerer projektets status
    }

    public void setId(int id) {
        this.id = id; // Sætter projektets ID
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id; // Sætter brugerens ID
    }

    public void setName(String name) {
        this.name = name; // Sætter projektets navn
    }

    public void setDescription(String description) {
        this.description = description; // Sætter projektets beskrivelse
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate; // Sætter projektets startdato
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate; // Sætter projektets slutdato
    }

    public void setStatus(String status) {
        this.status = status; // Sætter projektets status
    }

    // Overrider toString metoden til at returnere en tekstbeskrivelse af projektet
    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}
