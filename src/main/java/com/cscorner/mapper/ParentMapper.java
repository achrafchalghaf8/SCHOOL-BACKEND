package com.cscorner.mapper;

import com.cscorner.dto.ParentDTO;
import com.cscorner.entities.Compte;
import com.cscorner.entities.Parent;
import com.cscorner.entities.Role;

import org.springframework.stereotype.Component;

@Component
public class ParentMapper {

    public ParentDTO toDTO(Parent parent) {
        if (parent == null) {
            return null;
        }

        ParentDTO dto = new ParentDTO();
        dto.setId(parent.getId());
        dto.setEmail(parent.getCompte() != null ? parent.getCompte().getEmail() : null);
        dto.setNom(parent.getCompte() != null ? parent.getCompte().getNom() : null);
        dto.setPassword(parent.getCompte() != null ? parent.getCompte().getPassword() : null);
        dto.setTelephone(parent.getTelephone());

        return dto;
    }

    public Parent toEntity(ParentDTO dto) {
        if (dto == null) {
            return null;
        }

        Parent parent = new Parent();
        parent.setId(dto.getId());
        parent.setTelephone(dto.getTelephone());

        if (parent.getCompte() == null) {
            parent.setCompte(new Compte());
        }

        parent.getCompte().setId(dto.getId());
        parent.getCompte().setEmail(dto.getEmail());
        parent.getCompte().setNom(dto.getNom());
        parent.getCompte().setPassword(dto.getPassword());
        parent.getCompte().setRole(Role.PARENT);

        return parent;
    }
}