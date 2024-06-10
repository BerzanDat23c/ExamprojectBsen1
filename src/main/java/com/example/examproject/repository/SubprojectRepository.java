package com.example.examproject.repository;

import com.example.examproject.model.Subproject;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class SubprojectRepository {

    // Database URL, brugernavn og password injiceret fra konfigurationsfilerne
    @Value("${database.url}")
    private String dbUrl;

    @Value("${database.username}")
    private String dbUsername;

    @Value("${database.password}")
    private String dbPassword;

    // Metode til at oprette et nyt underprojekt i databasen
    public Subproject createSubproject(Subproject subproject) {
        // Opretter forbindelse til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String SQL = "INSERT INTO subproject (project_id, name, description, status, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametrene i SQL-forespørgslen med værdierne fra subprojektet
            ps.setInt(1, subproject.getProject_Id());
            ps.setString(2, subproject.getName());
            ps.setString(3, subproject.getDescription());
            ps.setString(4, subproject.getStatus());
            ps.setDate(5, Date.valueOf(subproject.getStartDate()));
            ps.setDate(6, Date.valueOf(subproject.getEndDate()));

            // Udfører SQL-forespørgslen
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Oprettelse af subprojektet mislykkedes, ingen rækker påvirket.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subproject;
    }

    // Metode til at hente alle underprojekter for et specifikt projekt fra databasen
    public ArrayList<Subproject> getAllSubprojects(int projectId) {
        ArrayList<Subproject> subprojects = new ArrayList<>();
        String SQL = "SELECT * FROM subproject WHERE project_id = ?";
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametret i SQL-forespørgslen med projektID
            ps.setInt(1, projectId);
            ResultSet resultSet = ps.executeQuery();

            // Itererer over resultatsættet og opretter Subproject-objekter
            while (resultSet.next()) {
                Subproject subproject = new Subproject();
                subproject.setId(resultSet.getInt("ID"));
                subproject.setProjectId(resultSet.getInt("PROJECT_ID"));
                subproject.setName(resultSet.getString("NAME"));
                subproject.setDescription(resultSet.getString("DESCRIPTION"));
                subproject.setStatus(resultSet.getString("STATUS"));
                subproject.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
                subproject.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
                subprojects.add(subproject);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af subprojekter fra databasen", e);
        }
        return subprojects;
    }

    // Metode til at opdatere et eksisterende underprojekt i databasen
    public Subproject updateSubproject(Subproject subproject) {
        String SQL = "UPDATE subproject SET name=?, description=?, status=?, start_date=?, end_date=? WHERE ID=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametrene i SQL-forespørgslen med værdierne fra subprojektet
            ps.setString(1, subproject.getName());
            ps.setString(2, subproject.getDescription());
            ps.setString(3, subproject.getStatus());
            ps.setDate(4, Date.valueOf(subproject.getStartDate()));
            ps.setDate(5, Date.valueOf(subproject.getEndDate()));
            ps.setInt(6, subproject.getId());

            // Udfører SQL-forespørgslen
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Opdatering af subprojekt mislykkedes, ingen rækker påvirket.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subproject;
    }

    // Metode til at slette et underprojekt fra databasen
    public void deleteSubproject(int subprojectId) {
        String SQL = "DELETE FROM subproject WHERE ID=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametret i SQL-forespørgslen med subprojektID
            ps.setInt(1, subprojectId);

            // Udfører SQL-forespørgslen
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Sletning af subprojekt mislykkedes, ingen rækker påvirket.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved sletning af subprojekt", e);
        }
    }

    // Metode til at hente et underprojekt baseret på dets ID fra databasen
    public Subproject getSubprojectById(int id) {
        Subproject subproject = null;
        String SQL = "SELECT * FROM subproject WHERE ID=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametret i SQL-forespørgslen med subprojektID
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();

            // Hvis underprojektet findes, opretter et Subproject-objekt
            if (resultSet.next()) {
                subproject = new Subproject();
                subproject.setId(resultSet.getInt("ID"));
                subproject.setName(resultSet.getString("NAME"));
                subproject.setDescription(resultSet.getString("DESCRIPTION"));
                subproject.setStatus(resultSet.getString("STATUS"));
                subproject.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
                subproject.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
                subproject.setProjectId(resultSet.getInt("PROJECT_ID"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subproject;
    }
}
