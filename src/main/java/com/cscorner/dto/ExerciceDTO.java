package com.cscorner.dto;

import java.util.Date;
import java.util.List;

public class ExerciceDTO {
    private Long id;
    private String contenu;
    private Date datePublication;
    private String fichier;
    private List<Long> classeIds;

    // **Ajout du champ courId**
    private Long courId;

    public ExerciceDTO() {}

    public ExerciceDTO(Long id, String contenu, Date datePublication, String fichier, List<Long> classeIds, Long courId) {
        this.id = id;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.fichier = fichier;
        this.classeIds = classeIds;
        this.courId = courId;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public Date getDatePublication() { return datePublication; }
    public void setDatePublication(Date datePublication) { this.datePublication = datePublication; }

    public String getFichier() { return fichier; }
    public void setFichier(String fichier) { this.fichier = fichier; }

    public List<Long> getClasseIds() { return classeIds; }
    public void setClasseIds(List<Long> classeIds) { this.classeIds = classeIds; }

    public Long getCourId() { return courId; }
    public void setCourId(Long courId) { this.courId = courId; }
}
