package com.cscorner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "cours")
public class Cour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

       @Lob
    @Column(name = "fichier", nullable = false, columnDefinition = "LONGTEXT")
    @NotBlank(message = "Le fichier est obligatoire")
    @Size(
        max = 33554432, // 25 Mo en base64 ≈ 33 554 432 caractères
        message = "Le fichier est trop volumineux (max 25MB en base64)"
    )
    private String fichier;

    private String matiere;

    @OneToMany(mappedBy = "cour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercice> exercices;

    public Cour() {}

    public Cour(Long id, String fichier, String matiere, List<Exercice> exercices) {
        this.id = id;
        this.fichier = fichier;
        this.matiere = matiere;
        this.exercices = exercices;
    }

    // Getters et Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFichier() { return fichier; }
    public void setFichier(String fichier) { this.fichier = fichier; }

    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }

    public List<Exercice> getExercices() { return exercices; }
    public void setExercices(List<Exercice> exercices) { this.exercices = exercices; }
}
