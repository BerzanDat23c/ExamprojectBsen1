// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.repository'
package com.example.examproject.repository;

// Importerer nødvendige klasser fra andre pakker og Java-biblioteket
import com.example.examproject.model.Task;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

// Denne linje fortæller, at denne klasse arbejder med data fra databasen
@Repository
public class TaskRepository {

    // Her gemmer vi information om, hvordan vi forbinder til databasen
    @Value("jdbc:mysql://localhost:3306/projectmanager_db")
    private String dbUrl; // URL til databasen

    @Value("root")
    private String dbUsername; // Brugernavn til databasen

    @Value("Dru58tet@")
    private String dbPassword; // Password til databasen

    // Denne metode opretter en ny opgave i databasen
    public Task createTask(Task task) {
        // Forbinder til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        // SQL-sætning for at indsætte en ny opgave
        String SQL = "INSERT INTO task (subproject_id, description, status, priority, estimated_time) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter værdierne fra opgaven ind i SQL-sætningen
            ps.setInt(1, task.getSubProject_Id());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getStatus());
            ps.setString(4, task.getPriority());
            ps.setInt(5, task.getEstimatedTime());

            // Udfører SQL-sætningen og opretter opgaven
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Kunne ikke oprette opgaven."); // Fejl hvis opgaven ikke blev oprettet
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Udskriver fejlen hvis noget går galt
        }
        return task; // Returnerer den oprettede opgave
    }

    // Denne metode henter alle opgaver for et bestemt underprojekt
    public ArrayList<Task> getAllTasks(int subProjectId) {
        ArrayList<Task> tasks = new ArrayList<>(); // Liste til at gemme opgaver
        // SQL-sætning for at hente opgaver
        String SQL = "SELECT * FROM task WHERE subproject_id = ?";
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword); // Forbinder til databasen

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, subProjectId); // Sætter underprojektets ID ind i SQL-sætningen
            ResultSet resultSet = ps.executeQuery(); // Udfører SQL-sætningen og får resultatet

            // Gennemgår resultatet og laver opgave-objekter
            while (resultSet.next()) {
                Task task = new Task(); // Opretter en ny opgave
                task.setId(resultSet.getInt("ID")); // Sætter ID for opgaven
                task.setSubProject_Id(resultSet.getInt("SUBPROJECT_ID")); // Sætter underprojektets ID
                task.setDescription(resultSet.getString("DESCRIPTION")); // Sætter beskrivelse for opgaven
                task.setStatus(resultSet.getString("STATUS")); // Sætter status for opgaven
                task.setPriority(resultSet.getString("PRIORITY")); // Sætter prioritet for opgaven
                task.setEstimatedTime(resultSet.getInt("ESTIMATED_TIME")); // Sætter estimeret tid for opgaven
                tasks.add(task); // Tilføjer opgaven til listen
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af opgaver fra databasen", e);
        }
        return tasks; // Returnerer listen af opgaver
    }

    // Denne metode opdaterer en eksisterende opgave i databasen
    public Task updateTask(Task task) {
        // SQL-sætning for at opdatere en opgave
        String SQL = "UPDATE task SET description=?, status=?, priority=?, estimated_time=? WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter værdierne fra opgaven ind i SQL-sætningen
            ps.setString(1, task.getDescription());
            ps.setString(2, task.getStatus());
            ps.setString(3, task.getPriority());
            ps.setInt(4, task.getEstimatedTime());
            ps.setInt(5, task.getId());

            // Udfører SQL-sætningen og opdaterer opgaven
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Kunne ikke opdatere opgaven."); // Fejl hvis opgaven ikke blev opdateret
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Kaster en fejl hvis noget går galt
        }
        return task; // Returnerer den opdaterede opgave
    }

    // Denne metode sletter en opgave fra databasen
    public void deleteTask(int taskId) {
        // SQL-sætning for at slette en opgave
        String SQL = "DELETE FROM task WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, taskId); // Sætter opgavens ID ind i SQL-sætningen

            // Udfører SQL-sætningen og sletter opgaven
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Kunne ikke slette opgaven."); // Fejl hvis opgaven ikke blev slettet
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Kaster en fejl hvis noget går galt
        }
    }

    // Denne metode henter en opgave baseret på dens ID fra databasen
    public Task getTaskById(int taskId) {
        Task task = null; // Variabel til at gemme opgaven
        // SQL-sætning for at hente en opgave
        String SQL = "SELECT * FROM task WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, taskId); // Sætter opgavens ID ind i SQL-sætningen
            ResultSet resultSet = ps.executeQuery(); // Udfører SQL-sætningen og får resultatet

            // Hvis opgaven findes, laver et opgave-objekt med dens data
            if (resultSet.next()) {
                task = new Task(); // Opretter en ny opgave
                task.setId(resultSet.getInt("ID")); // Sætter ID for opgaven
                task.setSubProject_Id(resultSet.getInt("SUBPROJECT_ID")); // Sætter underprojektets ID
                task.setDescription(resultSet.getString("DESCRIPTION")); // Sætter beskrivelse for opgaven
                task.setStatus(resultSet.getString("STATUS")); // Sætter status for opgaven
                task.setPriority(resultSet.getString("PRIORITY")); // Sætter prioritet for opgaven
                task.setEstimatedTime(resultSet.getInt("ESTIMATED_TIME")); // Sætter estimeret tid for opgaven
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Kaster en fejl hvis noget går galt
        }
        return task; // Returnerer den fundne opgave
    }
}
