package com.cscorner.dto;

import java.util.List;

public class ClasseDTO {
    private Long id;
    private String niveau;
    private List<Long> enseignantIds;

    public ClasseDTO() {}

    public ClasseDTO(Long id, String niveau) {
        this.id = id;
        this.niveau = niveau;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public List<Long> getEnseignantIds() { return enseignantIds; }
    public void setEnseignantIds(List<Long> enseignantIds) { this.enseignantIds = enseignantIds; }
}