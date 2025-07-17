package com.cscorner.dto;

import com.cscorner.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompteDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String email;
    private String nom;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Role role;

    // Constructeurs
    public CompteDTO() {}

    public CompteDTO(Long id, String email, String nom, String password, Role role) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.role = role;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @Override
    public String toString() {
        return "CompteDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}