package com.cscorner.dto;

import java.util.Date;

public class EmploiDTO {
    private Long id;
    private Date datePublication;
    private String fichier;
    private Long classeId;

    public EmploiDTO() {}

    public EmploiDTO(Long id, Date datePublication, String fichier, Long classeId) {
        this.id = id;
        this.datePublication = datePublication;
        this.fichier = fichier;
        this.classeId = classeId;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }

    public String getFichier() { return fichier; }
    public void setFichier(String fichier) { this.fichier = fichier; }

    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }
}
