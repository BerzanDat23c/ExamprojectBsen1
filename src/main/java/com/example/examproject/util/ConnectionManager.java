// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.util'
package com.example.examproject.util;

// Importerer nødvendige klasser fra Java-biblioteket
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Denne klasse hjælper os med at forbinde til databasen
public class ConnectionManager {
    private static Connection conn; // En variabel til at gemme databaseforbindelsen

    // Denne konstruktør er privat, så ingen kan lave en ny instans af denne klasse
    private ConnectionManager() {
        // default constructor gøres private - klasse kan ikke instantieres
    }

    // Denne metode laver en forbindelse til databasen
    public static Connection getConnection(String db_url, String uid, String pwd) {
        // Hvis vi allerede har en forbindelse, så returner den
        if (conn != null) return conn;

        try {
            // Opretter en ny forbindelse til databasen
            conn = DriverManager.getConnection(db_url, uid, pwd);
        } catch (SQLException e) {
            // Hvis der sker en fejl, udskriv en besked og fejlen
            System.out.println("Couldn't connect to db");
            e.printStackTrace();
        }
        return conn; // Returnerer forbindelsen
    }
}
