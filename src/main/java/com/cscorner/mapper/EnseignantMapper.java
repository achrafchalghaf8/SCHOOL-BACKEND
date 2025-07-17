package com.cscorner.mapper;

import com.cscorner.dto.EnseignantDTO;
import com.cscorner.entities.Classe;
import com.cscorner.entities.Compte;
import com.cscorner.entities.Enseignant;
import com.cscorner.entities.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EnseignantMapper {

    public EnseignantDTO toDTO(Enseignant enseignant) {
        if (enseignant == null) {
            return null;
        }

        EnseignantDTO dto = new EnseignantDTO();
        dto.setId(enseignant.getId());
        dto.setEmail(enseignant.getCompte() != null ? enseignant.getCompte().getEmail() : null);
        dto.setNom(enseignant.getCompte() != null ? enseignant.getCompte().getNom() : null);
        dto.setPassword(enseignant.getCompte() != null ? enseignant.getCompte().getPassword() : null);
        dto.setSpecialite(enseignant.getSpecialite());
        dto.setTelephone(enseignant.getTelephone());
        
        if (enseignant.getClasses() != null) {
            dto.setClasseIds(enseignant.getClasses().stream()
                    .map(Classe::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Enseignant toEntity(EnseignantDTO dto) {
        if (dto == null) {
            return null;
        }

        Enseignant enseignant = new Enseignant();
        enseignant.setId(dto.getId());
        enseignant.setSpecialite(dto.getSpecialite());
        enseignant.setTelephone(dto.getTelephone());

        if (enseignant.getCompte() == null) {
            enseignant.setCompte(new Compte());
        }

        enseignant.getCompte().setId(dto.getId());
        enseignant.getCompte().setEmail(dto.getEmail());
        enseignant.getCompte().setNom(dto.getNom());
        enseignant.getCompte().setPassword(dto.getPassword());
        enseignant.getCompte().setRole(Role.ENSEIGNANT);

        return enseignant;
    }
}