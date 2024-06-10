package com.example.examproject.repository;

import com.example.examproject.model.Task;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class TaskRepository {

    // Database URL, brugernavn og password injiceret fra konfigurationsfilerne
    @Value("jdbc:mysql://localhost:3306/projectmanager_db")
    private String dbUrl;

    @Value("root")
    private String dbUsername;

    @Value("Dru58tet@")
    private String dbPassword;

    // Metode til at oprette en ny opgave i databasen
    public Task createTask(Task task) {
        // Opretter forbindelse til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String SQL = "INSERT INTO task (subproject_id, description, status, priority, estimated_time) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametrene i SQL-forespørgslen med værdierne fra task objektet
            ps.setInt(1, task.getSubProject_Id());
            ps.setString(2, task.getDescription());
            ps.setString(3, task.getStatus());
            ps.setString(4, task.getPriority());
            ps.setInt(5, task.getEstimatedTime());

            // Udfører SQL-forespørgslen
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Oprettelse af opgave mislykkedes, ingen rækker påvirket.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    // Metode til at hente alle opgaver for et specifikt underprojekt fra databasen
    public ArrayList<Task> getAllTasks(int subProjectId) {
        ArrayList<Task> tasks = new ArrayList<>();
        String SQL = "SELECT * FROM task WHERE subproject_id = ?";
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametret i SQL-forespørgslen med subprojectID
            ps.setInt(1, subProjectId);
            ResultSet resultSet = ps.executeQuery();

            // Itererer over resultatsættet og opretter Task-objekter
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("ID"));
                task.setSubProject_Id(resultSet.getInt("SUBPROJECT_ID"));
                task.setDescription(resultSet.getString("DESCRIPTION"));
                task.setStatus(resultSet.getString("STATUS"));
                task.setPriority(resultSet.getString("PRIORITY"));
                task.setEstimatedTime(resultSet.getInt("ESTIMATED_TIME"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fejl ved hentning af opgaver fra databasen", e);
        }
        return tasks;
    }

    // Metode til at opdatere en eksisterende opgave i databasen
    public Task updateTask(Task task) {
        String SQL = "UPDATE task SET description=?, status=?, priority=?, estimated_time=? WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametrene i SQL-forespørgslen med værdierne fra task objektet
            ps.setString(1, task.getDescription());
            ps.setString(2, task.getStatus());
            ps.setString(3, task.getPriority());
            ps.setInt(4, task.getEstimatedTime());
            ps.setInt(5, task.getId());

            // Udfører SQL-forespørgslen
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Opdatering af opgave mislykkedes, ingen rækker påvirket.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    // Metode til at slette en opgave fra databasen
    public void deleteTask(int taskId) {
        String SQL = "DELETE FROM task WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametret i SQL-forespørgslen med taskID
            ps.setInt(1, taskId);

            // Udfører SQL-forespørgslen
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Sletning af opgave mislykkedes, ingen rækker påvirket.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metode til at hente en opgave baseret på dens ID fra databasen
    public Task getTaskById(int taskId) {
        Task task = null;
        String SQL = "SELECT * FROM task WHERE id=?";
        try (Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametret i SQL-forespørgslen med taskID
            ps.setInt(1, taskId);
            ResultSet resultSet = ps.executeQuery();

            // Hvis opgaven findes, opretter et Task-objekt
            if (resultSet.next()) {
                task = new Task();
                task.setId(resultSet.getInt("ID"));
                task.setSubProject_Id(resultSet.getInt("SUBPROJECT_ID"));
                task.setDescription(resultSet.getString("DESCRIPTION"));
                task.setStatus(resultSet.getString("STATUS"));
                task.setPriority(resultSet.getString("PRIORITY"));
                task.setEstimatedTime(resultSet.getInt("ESTIMATED_TIME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }
}
