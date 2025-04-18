package fr.formation.jakarta.model.entity;


import java.io.Serializable;


public class UserWithoutJPA implements Serializable {
    private String username;
    private String email;

    public UserWithoutJPA(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", email=" + email + "]";
    }
}