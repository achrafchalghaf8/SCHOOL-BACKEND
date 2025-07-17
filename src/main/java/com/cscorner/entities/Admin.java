package com.cscorner.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Compte compte;

    public Admin() {}

    public Admin(String email, String nom, String password) {
        this.compte = new Compte(email, nom, password, Role.ADMIN);
    }

    public Admin(Long id, String email, String nom, String password) {
        this.compte = new Compte(id, email, nom, password, Role.ADMIN);
        this.id = id;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Compte getCompte() { return compte; }
    public void setCompte(Compte compte) { this.compte = compte; }

    // Méthodes de délégation pour faciliter l'accès aux propriétés du compte
    public String getEmail() { return compte != null ? compte.getEmail() : null; }
    public void setEmail(String email) { if (compte != null) compte.setEmail(email); }

    public String getNom() { return compte != null ? compte.getNom() : null; }
    public void setNom(String nom) { if (compte != null) compte.setNom(nom); }

    public String getPassword() { return compte != null ? compte.getPassword() : null; }
    public void setPassword(String password) { if (compte != null) compte.setPassword(password); }

    public Role getRole() { return compte != null ? compte.getRole() : null; }
    public void setRole(Role role) { if (compte != null) compte.setRole(role); }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", compte=" + compte +
                '}';
    }
}
