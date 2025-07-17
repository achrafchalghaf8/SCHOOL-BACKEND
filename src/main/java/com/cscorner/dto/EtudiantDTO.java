package com.cscorner.dto;

public class EtudiantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private Long parentId;
    private Long classeId;

    public EtudiantDTO() {}

    public EtudiantDTO(Long id, String nom, String prenom, Long parentId, Long classeId) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.parentId = parentId;
        this.classeId = classeId;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }
}
