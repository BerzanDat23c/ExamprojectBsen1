package com.example.examproject.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class Project {

    // Attributter for Project-klassen
    private int id; // Projektets ID
    private int users_id; // ID for brugeren, der oprettede projektet
    private String name; // Projektets navn
    private String description; // Beskrivelse af projektet
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // Startdato for projektet
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; // Slutdato for projektet
    private String status; // Status for projektet

    // Konstruktør med parametre til at initialisere alle attributter
    public Project(int id, int users_id, String name, String description, LocalDate startDate, LocalDate endDate, String status) {
        this.id = id;
        this.users_id = users_id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Standard konstruktør uden parametre
    public Project() {
    }

    // Gettere og settere for alle attributter
    public int getId() {
        return id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Overrider toString metoden til at returnere en strengrepræsentation af projektet
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
