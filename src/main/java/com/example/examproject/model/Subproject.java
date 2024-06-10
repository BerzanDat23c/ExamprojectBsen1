package com.example.examproject.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Subproject {

    // Attributter for Subproject-klassen
    private int id; // Underprojektets ID
    private int project_Id; // ID for projektet, som underprojektet hører til
    private String name; // Underprojektets navn
    private String description; // Beskrivelse af underprojektet
    private String status; // Status for underprojektet

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // Startdato for underprojektet
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; // Slutdato for underprojektet

    // Konstruktør med parametre til at initialisere alle attributter
    public Subproject(String name, String description, String status, LocalDate startDate, LocalDate endDate, int id, int project_Id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
        this.project_Id = project_Id;
    }

    // Standard konstruktør uden parametre
    public Subproject() {
    }

    // Gettere og settere for alle attributter
    public int getId() {
        return id;
    }

    public int getProject_Id() {
        return project_Id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProjectId(int project_Id) {
        this.project_Id = project_Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // Overrider toString metoden til at returnere en strengrepræsentation af underprojektet
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
