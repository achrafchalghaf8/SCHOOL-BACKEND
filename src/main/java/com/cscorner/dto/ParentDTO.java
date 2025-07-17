package com.cscorner.dto;

import com.cscorner.entities.Role;

public class ParentDTO extends CompteDTO {
    private String telephone;

    public ParentDTO() {
        super();
        setRole(Role.PARENT);
    }

    public ParentDTO(Long id, String email, String nom, String password, String telephone) {
        super(id, email, nom, password, Role.PARENT);
        this.telephone = telephone;
    }

    // Getters and Setters
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    @Override
    public String toString() {
        return "ParentDTO{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", role=" + getRole() +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}