// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.repository'
package com.example.examproject.repository;

// Importerer nødvendige klasser fra andre pakker og Java-biblioteket
import com.example.examproject.model.User;
import com.example.examproject.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

// Denne linje fortæller, at denne klasse arbejder med data fra databasen
@Repository
public class UserRepository {

    // Her gemmer vi information om, hvordan vi forbinder til databasen
    @Value("jdbc:mysql://localhost:3306/ProjectManager_db")
    private String dbUrl; // URL til databasen

    @Value("root")
    private String dbUsername; // Brugernavn til databasen

    @Value("Dru58tet@")
    private String dbPassword; // Password til databasen

    // Denne metode registrerer en ny bruger i databasen
    public User registerUser(User user) {
        // Forbinder til databasen
        Connection conn = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
        // SQL-sætning for at indsætte en ny bruger
        String SQL = "INSERT INTO users (ID, FIRST_NAME, USERNAME, PASSWORD) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            // Sætter værdierne fra brugeren ind i SQL-sætningen
            ps.setInt(1, user.getid());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());

            // Udfører SQL-sætningen og registrerer brugeren
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Registrering af bruger mislykkedes", e); // Fejl hvis brugeren ikke blev registreret
        }
        return user; // Returnerer den registrerede bruger
    }

    // Denne metode finder en bruger baseret på ID fra databasen
    public User findById(int id) {
        // Forbinder til databasen og forbereder SQL-sætningen
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            pstmt.setInt(1, id); // Sætter brugerens ID ind i SQL-sætningen

            // Udfører SQL-sætningen og henter resultatet
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs); // Opretter brugerobjekt fra resultatet
                } else {
                    return null; // Ingen bruger fundet
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Hentning af bruger baseret på ID mislykkedes", e); // Fejl hvis brugeren ikke blev fundet
        }
    }

    // Denne metode finder en bruger baseret på brugernavn fra databasen
    public User findByUsername(String username) {
        // Forbinder til databasen og forbereder SQL-sætningen
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            pstmt.setString(1, username); // Sætter brugerens brugernavn ind i SQL-sætningen

            // Udfører SQL-sætningen og henter resultatet
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs); // Opretter brugerobjekt fra resultatet
                } else {
                    return null; // Ingen bruger fundet
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Hentning af bruger baseret på brugernavn mislykkedes", e); // Fejl hvis brugeren ikke blev fundet
        }
    }

    // Hjælpemetode til at oprette et brugerobjekt fra et ResultSet
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        // Returnerer en ny bruger med data fra resultatet
        return new User(
                rs.getString("FIRST_NAME"), // Sætter brugerens fornavn
                rs.getString("USERNAME"), // Sætter brugerens brugernavn
                rs.getString("PASSWORD"), // Sætter brugerens password
                rs.getInt("ID") // Sætter brugerens ID
        );
    }
}
