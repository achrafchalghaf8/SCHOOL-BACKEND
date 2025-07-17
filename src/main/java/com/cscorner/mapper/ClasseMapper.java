package com.cscorner.mapper;

import com.cscorner.dto.ClasseDTO;
import com.cscorner.entities.Classe;
import com.cscorner.entities.Enseignant;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClasseMapper {

    public ClasseDTO toDTO(Classe entity) {
        ClasseDTO dto = new ClasseDTO(entity.getId(), entity.getNiveau());
        if (entity.getEnseignants() != null) {
            dto.setEnseignantIds(entity.getEnseignants().stream()
                    .map(Enseignant::getId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Classe toEntity(ClasseDTO dto) {
        return new Classe(dto.getId(), dto.getNiveau());
    }
}