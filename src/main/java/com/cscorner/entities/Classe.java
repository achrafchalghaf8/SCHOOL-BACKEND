package com.cscorner.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classes")
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String niveau;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etudiant> etudiants = new ArrayList<>();

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emploi> emplois = new ArrayList<>();

    @ManyToMany(mappedBy = "classes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Exercice> exercices = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "enseignant_classe",
        joinColumns = @JoinColumn(name = "classe_id"),
        inverseJoinColumns = @JoinColumn(name = "enseignant_id")
    )
    private List<Enseignant> enseignants = new ArrayList<>();

    public Classe() {}

    public Classe(Long id, String niveau) {
        this.id = id;
        this.niveau = niveau;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public List<Etudiant> getEtudiants() { return etudiants; }
    public void setEtudiants(List<Etudiant> etudiants) { this.etudiants = etudiants; }

    public List<Emploi> getEmplois() { return emplois; }
    public void setEmplois(List<Emploi> emplois) { this.emplois = emplois; }

    public List<Exercice> getExercices() { return exercices; }
    public void setExercices(List<Exercice> exercices) { this.exercices = exercices; }

    public List<Enseignant> getEnseignants() { return enseignants; }
    public void setEnseignants(List<Enseignant> enseignants) { this.enseignants = enseignants; }
}