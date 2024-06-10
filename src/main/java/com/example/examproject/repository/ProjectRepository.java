package com.example.examproject.repository;

import com.example.examproject.model.Project;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class ProjectRepository {

    // Database URL, brugernavn og password injiceret fra konfigurationsfilerne
    @Value("jdbc:mysql://localhost:3306/ProjectManager_db")
    private String dbUrl;

    @Value("root")
    private String dbUsername;

    @Value("Dru58tet@")
    private String dbPassword;

    // Metode til at oprette et nyt projekt i databasen
    public Project createProject(Project project) {
        // Opretter forbindelse til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String SQL = "INSERT INTO project (users_id, name, description, status, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametrene i SQL-forespørgslen med værdierne fra projektet
            ps.setInt(1, project.getUsers_id());
            ps.setString(2, project.getName());
            ps.setString(3, project.getDescription());
            ps.setString(4, project.getStatus());
            ps.setDate(5, Date.valueOf(project.getStartDate()));
            ps.setDate(6, Date.valueOf(project.getEndDate()));

            // Udfører SQL-forespørgslen
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Oprettelse af projekt mislykkedes, ingen rækker påvirket.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    // Metode til at hente alle projekter for en specifik bruger fra databasen
    public ArrayList<Project> getAllProjects(int userId) {
        // Opretter forbindelse til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        ArrayList<Project> projects = new ArrayList<>();
        String SQL = "SELECT * FROM project WHERE users_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();

            // Itererer over resultatsættet og opretter Project-objekter
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("ID"));
                project.setUsers_id(resultSet.getInt("USERS_ID"));
                project.setName(resultSet.getString("NAME"));
                project.setDescription(resultSet.getString("DESCRIPTION"));
                project.setStatus(resultSet.getString("STATUS"));
                project.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
                project.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af projekter fra databasen", e);
        }
        return projects;
    }

    // Metode til at opdatere et eksisterende projekt i databasen
    public Project updateProject(Project project) {
        String SQL = "UPDATE project SET NAME=?, DESCRIPTION=?, STATUS=?, START_DATE=?, END_DATE=? WHERE ID=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametrene i SQL-forespørgslen med værdierne fra projektet
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getStatus());
            ps.setDate(4, Date.valueOf(project.getStartDate()));
            ps.setDate(5, Date.valueOf(project.getEndDate()));
            ps.setInt(6, project.getId());

            // Udfører SQL-forespørgslen
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Opdatering af projekt mislykkedes, ingen rækker påvirket.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return project;
    }

    // Metode til at slette et projekt fra databasen
    public void deleteProject(int projectId) {
        String SQL = "DELETE FROM project WHERE ID=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, projectId);

            // Udfører SQL-forespørgslen
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Sletning af projekt mislykkedes, ingen rækker påvirket.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metode til at hente et projekt baseret på dets ID fra databasen
    public Project getProjectById(int projectId) {
        Project project = null;
        String SQL = "SELECT * FROM project WHERE ID=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, projectId);
            ResultSet resultSet = ps.executeQuery();

            // Hvis projektet findes, opretter et Project-objekt
            if (resultSet.next()) {
                project = new Project();
                project.setId(resultSet.getInt("ID"));
                project.setUsers_id(resultSet.getInt("USERS_ID"));
                project.setName(resultSet.getString("NAME"));
                project.setDescription(resultSet.getString("DESCRIPTION"));
                project.setStatus(resultSet.getString("STATUS"));
                project.setStartDate(resultSet.getDate("START_DATE").toLocalDate());
                project.setEndDate(resultSet.getDate("END_DATE").toLocalDate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return project;
    }
}
