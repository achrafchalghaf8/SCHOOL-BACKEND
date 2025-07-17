package com.cscorner.mapper;

import com.cscorner.dto.EmploiDTO;
import com.cscorner.entities.Classe;
import com.cscorner.entities.Emploi;
import com.cscorner.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmploiMapper {

    @Autowired
    private ClasseRepository classeRepository;

    public EmploiDTO toDTO(Emploi emploi) {
        return new EmploiDTO(
                emploi.getId(),
                emploi.getDatePublication(),
                emploi.getFichier(),
                emploi.getClasse().getId()
        );
    }

    public Emploi toEntity(EmploiDTO dto) {
        Classe classe = classeRepository.findById(dto.getClasseId()).orElseThrow(
                () -> new RuntimeException("Classe introuvable avec ID: " + dto.getClasseId())
        );

        return new Emploi(
                dto.getId(),
                dto.getDatePublication(),
                dto.getFichier(),
                classe
        );
    }
}
