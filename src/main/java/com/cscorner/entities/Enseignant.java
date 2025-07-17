package com.cscorner.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enseignants")
public class Enseignant {

    @Id
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = false)
    @MapsId
    @JoinColumn(name = "id")
    private Compte compte;

    @Column(nullable = false)
    private String specialite;

    @Column(nullable = false)
    private String telephone;

    @ManyToMany
    @JoinTable(
        name = "enseignant_classe",
        joinColumns = @JoinColumn(name = "enseignant_id"),
        inverseJoinColumns = @JoinColumn(name = "classe_id")
    )
    private List<Classe> classes = new ArrayList<>();

    public Enseignant() {}

    public Enseignant(String specialite, String telephone) {
        this.specialite = specialite;
        this.telephone = telephone;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Compte getCompte() { return compte; }
    public void setCompte(Compte compte) { this.compte = compte; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public List<Classe> getClasses() { return classes; }
    public void setClasses(List<Classe> classes) { this.classes = classes; }

    // Méthodes pratiques pour accéder aux propriétés du compte
    public String getEmail() { return compte != null ? compte.getEmail() : null; }
    public String getNom() { return compte != null ? compte.getNom() : null; }
    public String getPassword() { return compte != null ? compte.getPassword() : null; }
    public Role getRole() { return compte != null ? compte.getRole() : null; }

    @Override
    public String toString() {
        return "Enseignant{" +
                "id=" + id +
                ", email='" + getEmail() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", specialite='" + specialite + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}