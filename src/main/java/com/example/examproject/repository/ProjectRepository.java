// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.repository'
package com.example.examproject.repository;

// Importerer nødvendige klasser fra andre pakker og Java-biblioteket
import com.example.examproject.model.Project;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

// Denne linje fortæller, at denne klasse arbejder med data fra databasen
@Repository
public class ProjectRepository {

    // Her gemmer vi information om, hvordan vi forbinder til databasen
    @Value("jdbc:mysql://localhost:3306/ProjectManager_db")
    private String dbUrl; // URL til databasen

    @Value("root")
    private String dbUsername; // Brugernavn til databasen

    @Value("Dru58tet@")
    private String dbPassword; // Password til databasen

    // Denne metode opretter et nyt projekt i databasen
    public Project createProject(Project project) {
        // Opretter forbindelse til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        // SQL-sætning for at indsætte et nyt projekt
        String SQL = "INSERT INTO project (users_id, name, description, status, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter værdierne fra projektet ind i SQL-sætningen
            ps.setInt(1, project.getUsers_id());
            ps.setString(2, project.getName());
            ps.setString(3, project.getDescription());
            ps.setString(4, project.getStatus());
            ps.setDate(5, Date.valueOf(project.getStartDate()));
            ps.setDate(6, Date.valueOf(project.getEndDate()));

            // Udfører SQL-sætningen og opretter projektet
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Kunne ikke oprette projektet."); // Fejl hvis projektet ikke blev oprettet
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Udskriver fejlen hvis noget går galt
        }
        return project; // Returnerer det oprettede projekt
    }

    // Denne metode henter alle projekter for en bestemt bruger
    public ArrayList<Project> getAllProjects(int userId) {
        // Opretter forbindelse til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        ArrayList<Project> projects = new ArrayList<>(); // Liste til at gemme projekter
        // SQL-sætning for at hente projekter
        String SQL = "SELECT * FROM project WHERE users_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, userId); // Sætter brugerens ID ind i SQL-sætningen
            ResultSet resultSet = ps.executeQuery(); // Udfører SQL-sætningen og får resultatet

            // Gennemgår resultatet og laver projekt-objekter
            while (resultSet.next()) {
                Project project = new Project(); // Opretter et nyt projekt
                project.setId(resultSet.getInt("ID")); // Sætter ID for projektet
                project.setUsers_id(resultSet.getInt("USERS_ID")); // Sætter brugerens ID
                project.setName(resultSet.getString("NAME")); // Sætter navn for projektet
                project.setDescription(resultSet.getString("DESCRIPTION")); // Sætter beskrivelse for projektet
                project.setStatus(resultSet.getString("STATUS")); // Sætter status for projektet
                project.setStartDate(resultSet.getDate("START_DATE").toLocalDate()); // Sætter startdato for projektet
                project.setEndDate(resultSet.getDate("END_DATE").toLocalDate()); // Sætter slutdato for projektet
                projects.add(project); // Tilføjer projektet til listen
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af projekter fra databasen", e);
        }
        return projects; // Returnerer listen af projekter
    }

    // Denne metode opdaterer et eksisterende projekt i databasen
    public Project updateProject(Project project) {
        // SQL-sætning for at opdatere et projekt
        String SQL = "UPDATE project SET NAME=?, DESCRIPTION=?, STATUS=?, START_DATE=?, END_DATE=? WHERE ID=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter værdierne fra projektet ind i SQL-sætningen
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getStatus());
            ps.setDate(4, Date.valueOf(project.getStartDate()));
            ps.setDate(5, Date.valueOf(project.getEndDate()));
            ps.setInt(6, project.getId());

            // Udfører SQL-sætningen og opdaterer projektet
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Kunne ikke opdatere projektet."); // Fejl hvis projektet ikke blev opdateret
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Kaster en fejl hvis noget går galt
        }
        return project; // Returnerer det opdaterede projekt
    }

    // Denne metode sletter et projekt fra databasen
    public void deleteProject(int projectId) {
        // SQL-sætning for at slette et projekt
        String SQL = "DELETE FROM project WHERE ID=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, projectId); // Sætter projektets ID ind i SQL-sætningen

            // Udfører SQL-sætningen og sletter projektet
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Kunne ikke slette projektet."); // Fejl hvis projektet ikke blev slettet
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Kaster en fejl hvis noget går galt
        }
    }

    // Denne metode henter et projekt baseret på dets ID fra databasen
    public Project getProjectById(int projectId) {
        Project project = null; // Variabel til at gemme projektet
        // SQL-sætning for at hente et projekt
        String SQL = "SELECT * FROM project WHERE ID=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, projectId); // Sætter projektets ID ind i SQL-sætningen
            ResultSet resultSet = ps.executeQuery(); // Udfører SQL-sætningen og får resultatet

            // Hvis projektet findes, laver et projekt-objekt med dets data
            if (resultSet.next()) {
                project = new Project(); // Opretter et nyt projekt
                project.setId(resultSet.getInt("ID")); // Sætter ID for projektet
                project.setUsers_id(resultSet.getInt("USERS_ID")); // Sætter brugerens ID
                project.setName(resultSet.getString("NAME")); // Sætter navn for projektet
                project.setDescription(resultSet.getString("DESCRIPTION")); // Sætter beskrivelse for projektet
                project.setStatus(resultSet.getString("STATUS")); // Sætter status for projektet
                project.setStartDate(resultSet.getDate("START_DATE").toLocalDate()); // Sætter startdato for projektet
                project.setEndDate(resultSet.getDate("END_DATE").toLocalDate()); // Sætter slutdato for projektet
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Kaster en fejl hvis noget går galt
        }
        return project; // Returnerer det fundne projekt
    }
}
