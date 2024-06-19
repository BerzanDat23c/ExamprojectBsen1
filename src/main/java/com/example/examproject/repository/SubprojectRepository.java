// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.repository'
package com.example.examproject.repository;

// Importerer nødvendige klasser fra andre pakker og Java-biblioteket
import com.example.examproject.model.Subproject;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

// Denne linje fortæller, at denne klasse arbejder med data fra databasen
@Repository
public class SubprojectRepository {

    // Her gemmer vi information om, hvordan vi forbinder til databasen
    @Value("${database.url}")
    private String dbUrl; // URL til databasen

    @Value("${database.username}")
    private String dbUsername; // Brugernavn til databasen

    @Value("${database.password}")
    private String dbPassword; // Password til databasen

    // Denne metode opretter et nyt underprojekt i databasen
    public Subproject createSubproject(Subproject subproject) {
        // Forbinder til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        // SQL-sætning for at indsætte et nyt underprojekt
        String SQL = "INSERT INTO subproject (project_id, name, description, status, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter værdierne fra underprojektet ind i SQL-sætningen
            ps.setInt(1, subproject.getProject_Id());
            ps.setString(2, subproject.getName());
            ps.setString(3, subproject.getDescription());
            ps.setString(4, subproject.getStatus());
            ps.setDate(5, Date.valueOf(subproject.getStartDate()));
            ps.setDate(6, Date.valueOf(subproject.getEndDate()));

            // Udfører SQL-sætningen og opretter underprojektet
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Kunne ikke oprette underprojektet.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Udskriver fejlen hvis noget går galt
        }
        return subproject; // Returnerer det oprettede underprojekt
    }

    // Denne metode henter alle underprojekter for et bestemt projekt
    public ArrayList<Subproject> getAllSubprojects(int projectId) {
        ArrayList<Subproject> subprojects = new ArrayList<>(); // Liste til at gemme underprojekter
        String SQL = "SELECT * FROM subproject WHERE project_id = ?"; // SQL-sætning for at hente underprojekter
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword); // Forbinder til databasen

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, projectId); // Sætter projektets ID ind i SQL-sætningen
            ResultSet resultSet = ps.executeQuery(); // Udfører SQL-sætningen og får resultatet

            // Gennemgår resultatet og laver underprojekt-objekter
            while (resultSet.next()) {
                Subproject subproject = new Subproject(); // Opretter et nyt underprojekt
                subproject.setId(resultSet.getInt("ID")); // Sætter ID for underprojektet
                subproject.setProjectId(resultSet.getInt("PROJECT_ID")); // Sætter projektets ID
                subproject.setName(resultSet.getString("NAME")); // Sætter navn for underprojektet
                subproject.setDescription(resultSet.getString("DESCRIPTION")); // Sætter beskrivelse for underprojektet
                subproject.setStatus(resultSet.getString("STATUS")); // Sætter status for underprojektet
                subproject.setStartDate(resultSet.getDate("START_DATE").toLocalDate()); // Sætter startdato for underprojektet
                subproject.setEndDate(resultSet.getDate("END_DATE").toLocalDate()); // Sætter slutdato for underprojektet
                subprojects.add(subproject); // Tilføjer underprojektet til listen
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af underprojekter fra databasen", e);
        }
        return subprojects; // Returnerer listen af underprojekter
    }

    // Denne metode opdaterer et eksisterende underprojekt i databasen
    public Subproject updateSubproject(Subproject subproject) {
        String SQL = "UPDATE subproject SET name=?, description=?, status=?, start_date=?, end_date=? WHERE ID=?"; // SQL-sætning for at opdatere et underprojekt
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter værdierne fra underprojektet ind i SQL-sætningen
            ps.setString(1, subproject.getName());
            ps.setString(2, subproject.getDescription());
            ps.setString(3, subproject.getStatus());
            ps.setDate(4, Date.valueOf(subproject.getStartDate()));
            ps.setDate(5, Date.valueOf(subproject.getEndDate()));
            ps.setInt(6, subproject.getId());

            // Udfører SQL-sætningen og opdaterer underprojektet
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Kunne ikke opdatere underprojektet.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Kaster en fejl hvis noget går galt
        }
        return subproject; // Returnerer det opdaterede underprojekt
    }

    // Denne metode sletter et underprojekt fra databasen
    public void deleteSubproject(int subprojectId) {
        String SQL = "DELETE FROM subproject WHERE ID=?"; // SQL-sætning for at slette et underprojekt
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, subprojectId); // Sætter underprojektets ID ind i SQL-sætningen

            // Udfører SQL-sætningen og sletter underprojektet
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Kunne ikke slette underprojektet.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved sletning af underprojekt", e);
        }
    }

    // Denne metode henter et underprojekt baseret på dets ID fra databasen
    public Subproject getSubprojectById(int id) {
        Subproject subproject = null; // Variabel til at gemme underprojektet
        String SQL = "SELECT * FROM subproject WHERE ID=?"; // SQL-sætning for at hente et underprojekt
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, id); // Sætter underprojektets ID ind i SQL-sætningen
            ResultSet resultSet = ps.executeQuery(); // Udfører SQL-sætningen og får resultatet

            // Hvis underprojektet findes, laver et underprojekt-objekt med dets data
            if (resultSet.next()) {
                subproject = new Subproject(); // Opretter et nyt underprojekt
                subproject.setId(resultSet.getInt("ID")); // Sætter ID for underprojektet
                subproject.setName(resultSet.getString("NAME")); // Sætter navn for underprojektet
                subproject.setDescription(resultSet.getString("DESCRIPTION")); // Sætter beskrivelse for underprojektet
                subproject.setStatus(resultSet.getString("STATUS")); // Sætter status for underprojektet
                subproject.setStartDate(resultSet.getDate("START_DATE").toLocalDate()); // Sætter startdato for underprojektet
                subproject.setEndDate(resultSet.getDate("END_DATE").toLocalDate()); // Sætter slutdato for underprojektet
                subproject.setProjectId(resultSet.getInt("PROJECT_ID")); // Sætter projektets ID
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Kaster en fejl hvis noget går galt
        }
        return subproject; // Returnerer det fundne underprojekt
    }
}
