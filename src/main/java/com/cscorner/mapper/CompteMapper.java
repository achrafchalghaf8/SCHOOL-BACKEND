package com.cscorner.mapper;

import org.springframework.stereotype.Component;
import com.cscorner.dto.CompteDTO;
import com.cscorner.entities.Compte;

@Component
public class CompteMapper {
    public CompteDTO toDTO(Compte compte) {
        return new CompteDTO(
                compte.getId(),
                compte.getEmail(),
                compte.getNom(),
                compte.getPassword(),
                compte.getRole()
        );
    }

    public Compte toEntity(CompteDTO compteDTO) {
        return new Compte(
                compteDTO.getId(),
                compteDTO.getEmail(),
                compteDTO.getNom(),
                compteDTO.getPassword(),
                compteDTO.getRole()
        );
    }
}