package com.cscorner.dto;

import com.cscorner.entities.Role;
import java.util.List;

public class EnseignantDTO extends CompteDTO {
    private String specialite;
    private String telephone;
    private List<Long> classeIds;

    public EnseignantDTO() {
        super();
        setRole(Role.ENSEIGNANT);
    }

    public EnseignantDTO(Long id, String email, String nom, String password, String specialite, String telephone) {
        super(id, email, nom, password, Role.ENSEIGNANT);
        this.specialite = specialite;
        this.telephone = telephone;
    }

    // Getters and Setters
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public List<Long> getClasseIds() { return classeIds; }
    public void setClasseIds(List<Long> classeIds) { this.classeIds = classeIds; }

    @Override
    public String toString() {
        return "EnseignantDTO{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", role=" + getRole() +
                ", specialite='" + specialite + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}