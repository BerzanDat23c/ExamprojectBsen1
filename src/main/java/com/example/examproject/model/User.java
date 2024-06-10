package com.example.examproject.model;

public class User {

    // Attributter for User-klassen
    private String firstname; // Brugerens fornavn
    private String username; // Brugerens brugernavn
    private String password; // Brugerens kodeord
    private int id; // Brugerens unikke ID

    // Konstruktør med parametre til at initialisere alle attributter
    public User(String firstname, String username, String password, int id) {
        this.firstname = firstname;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    // Standard konstruktør uden parametre
    public User() {
    }

    // Gettere og settere for alle attributter
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    // Overrider toString metoden til at returnere en strengrepræsentation af brugeren
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
