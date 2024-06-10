package com.example.examproject.repository;

import com.example.examproject.model.User;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {

    // Database URL, brugernavn og password injiceret fra konfigurationsfilerne
    @Value("jdbc:mysql://localhost:3306/ProjectManager_db")
    private String dbUrl;

    @Value("root")
    private String dbUsername;

    @Value("Dru58tet@")
    private String dbPassword;

    // Metode til at registrere en ny bruger i databasen
    public User registerUser(User user) {
        // Opretter forbindelse til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        String SQL = "INSERT INTO users (ID, FIRST_NAME, USERNAME, PASSWORD) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter parametrene i SQL-forespørgslen med værdierne fra user objektet
            ps.setInt(1, user.getid());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());

            // Udfører SQL-forespørgslen
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Registrering af bruger mislykkedes", e);
        }
        return user;
    }

    // Metode til at finde en bruger baseret på ID fra databasen
    public User findById(int id) {
        // Opretter forbindelse til databasen og forbereder SQL-forespørgslen
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            pstmt.setInt(1, id);
            // Udfører forespørgslen og henter resultatsættet
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Hentning af bruger baseret på ID mislykkedes", e);
        }
    }

    // Metode til at finde en bruger baseret på brugernavn fra databasen
    public User findByUsername(String username) {
        // Opretter forbindelse til databasen og forbereder SQL-forespørgslen
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            pstmt.setString(1, username);
            // Udfører forespørgslen og henter resultatsættet
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Hentning af bruger baseret på brugernavn mislykkedes", e);
        }
    }

    // Hjælpemetode til at oprette et User objekt fra et ResultSet
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("FIRST_NAME"),
                rs.getString("USERNAME"),
                rs.getString("PASSWORD"),
                rs.getInt("ID")
        );
    }
}
