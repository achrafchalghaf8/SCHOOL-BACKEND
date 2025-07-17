package com.cscorner.dto;

import com.cscorner.entities.Role;

public class AdminDTO extends CompteDTO {

    public AdminDTO() {
        super();
        setRole(Role.ADMIN);
    }

    public AdminDTO(Long id, String email, String nom, String password) {
        super(id, email, nom, password, Role.ADMIN);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
