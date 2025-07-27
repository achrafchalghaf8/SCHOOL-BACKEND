package com.cscorner.dto;

import java.util.List;

public class CourDTO {
    private Long id;
    private String fichier;
    private String matiere;
    private String proprietaire;
    private List<Long> exerciceIds;

    public CourDTO() {}

    public CourDTO(Long id, String fichier, String matiere, String proprietaire, List<Long> exerciceIds) {
        this.id = id;
        this.fichier = fichier;
        this.matiere = matiere;
        this.proprietaire = proprietaire;
        this.exerciceIds = exerciceIds;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFichier() { return fichier; }
    public void setFichier(String fichier) { this.fichier = fichier; }

    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }
    
    public String getProprietaire() { return proprietaire; }
    public void setProprietaire(String proprietaire) { this.proprietaire = proprietaire; }

    public List<Long> getExerciceIds() { return exerciceIds; }
    public void setExerciceIds(List<Long> exerciceIds) { this.exerciceIds = exerciceIds; }
}
