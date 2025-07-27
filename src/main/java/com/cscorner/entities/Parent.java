package com.cscorner.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parents")
public class Parent {

    @Id
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = false)
    @MapsId
    @JoinColumn(name = "id")
    private Compte compte;

    @Column(nullable = false)
    private String telephone;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etudiant> etudiants = new ArrayList<>();

    public Parent() {}

    public Parent(String telephone) {
        this.telephone = telephone;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Compte getCompte() { return compte; }
    public void setCompte(Compte compte) { this.compte = compte; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public List<Etudiant> getEtudiants() { return etudiants; }
    public void setEtudiants(List<Etudiant> etudiants) { this.etudiants = etudiants; }

    // Méthodes pratiques pour accéder aux propriétés du compte
    public String getEmail() { return compte != null ? compte.getEmail() : null; }
    public String getNom() { return compte != null ? compte.getNom() : null; }
    public String getPassword() { return compte != null ? compte.getPassword() : null; }
    public Role getRole() { return compte != null ? compte.getRole() : null; }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", email='" + getEmail() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}