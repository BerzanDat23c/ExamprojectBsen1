// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.model'
package com.example.examproject.model;

// Definerer en klasse kaldet 'Task' (opgave)
public class Task {
    // Variabler der holder information om opgaven
    private String description; // Beskrivelse af opgaven
    private String status; // Status for opgaven (f.eks. 'In Progress', 'Completed')
    private String priority; // Prioritet for opgaven (f.eks. 'High', 'Medium', 'Low')
    private int estimatedTime; // Estimeret tid til at fuldføre opgaven (i timer)
    private int id; // Opgavens ID (en unik identifikator)
    private int subProject_Id; // ID for underprojektet, som opgaven hører til

    // Dette er en standard 'konstruktør', der ikke tager nogen parametre
    public Task() {
    }

    // Dette er en 'konstruktør', der bruges til at lave en ny opgave med nogle informationer
    public Task(String description, String status, String priority, int estimatedTime, int id) {
        this.description = description; // Sætter opgavens beskrivelse
        this.status = status; // Sætter opgavens status
        this.priority = priority; // Sætter opgavens prioritet
        this.estimatedTime = estimatedTime; // Sætter den estimerede tid
        this.id = id; // Sætter opgavens ID
    }

    // Dette er en 'konstruktør', der bruges til at lave en ny opgave med alle informationerne
    public Task(String description, String status, String priority, int estimatedTime, int id, int subProject_Id) {
        this.description = description; // Sætter opgavens beskrivelse
        this.status = status; // Sætter opgavens status
        this.priority = priority; // Sætter opgavens prioritet
        this.estimatedTime = estimatedTime; // Sætter den estimerede tid
        this.id = id; // Sætter opgavens ID
        this.subProject_Id = subProject_Id; // Sætter ID for underprojektet, som opgaven hører til
    }

    // Gettere og settere - disse bruges til at få og sætte værdierne for variablerne
    public String getDescription() {
        return description; // Returnerer opgavens beskrivelse
    }

    public void setDescription(String description) {
        this.description = description; // Sætter opgavens beskrivelse
    }

    public String getStatus() {
        return status; // Returnerer opgavens status
    }

    public void setStatus(String status) {
        this.status = status; // Sætter opgavens status
    }

    public String getPriority() {
        return priority; // Returnerer opgavens prioritet
    }

    public void setPriority(String priority) {
        this.priority = priority; // Sætter opgavens prioritet
    }

    public int getId() {
        return id; // Returnerer opgavens ID
    }

    public void setId(int id) {
        this.id = id; // Sætter opgavens ID
    }

    public int getSubProject_Id() {
        return subProject_Id; // Returnerer ID for underprojektet, som opgaven hører til
    }

    public void setSubProject_Id(int subProject_Id) {
        this.subProject_Id = subProject_Id; // Sætter ID for underprojektet, som opgaven hører til
    }

    public int getEstimatedTime() {
        return estimatedTime; // Returnerer den estimerede tid
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime; // Sætter den estimerede tid
    }

    // Overrider toString metoden til at returnere en tekstbeskrivelse af opgaven
    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", id=" + id +
                ", subProject_Id=" + subProject_Id +
                '}';
    }
}
