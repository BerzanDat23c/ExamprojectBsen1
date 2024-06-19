// Denne linje fortæller, at denne fil er en del af pakken 'com.example.examproject.model'
package com.example.examproject.model;

// Definerer en klasse kaldet 'User' (bruger)
public class User {

    // Variabler der holder information om brugeren
    private String firstname; // Brugerens fornavn
    private String username; // Brugerens brugernavn
    private String password; // Brugerens kodeord
    private int id; // Brugerens unikke ID

    // Dette er en 'konstruktør', der bruges til at lave en ny bruger med alle informationerne
    public User(String firstname, String username, String password, int id) {
        this.firstname = firstname; // Sætter brugerens fornavn
        this.username = username; // Sætter brugerens brugernavn
        this.password = password; // Sætter brugerens kodeord
        this.id = id; // Sætter brugerens ID
    }

    // Dette er en standard 'konstruktør', der ikke tager nogen parametre
    public User() {
    }

    // Gettere og settere - disse bruges til at få og sætte værdierne for variablerne
    public String getFirstname() {
        return firstname; // Returnerer brugerens fornavn
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname; // Sætter brugerens fornavn
    }

    public String getUsername() {
        return username; // Returnerer brugerens brugernavn
    }

    public void setUsername(String username) {
        this.username = username; // Sætter brugerens brugernavn
    }

    public String getPassword() {
        return password; // Returnerer brugerens kodeord
    }

    public void setPassword(String password) {
        this.password = password; // Sætter brugerens kodeord
    }

    public int getid() {
        return id; // Returnerer brugerens ID
    }

    public void setid(int id) {
        this.id = id; // Sætter brugerens ID
    }

    // Overrider toString metoden til at returnere en tekstbeskrivelse af brugeren
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
