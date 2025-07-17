package com.cscorner.mapper;

import com.cscorner.dto.EtudiantDTO;
import com.cscorner.entities.Classe;
import com.cscorner.entities.Etudiant;
import com.cscorner.entities.Parent;
import com.cscorner.repository.ClasseRepository;
import com.cscorner.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EtudiantMapper {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ClasseRepository classeRepository;

    public EtudiantDTO toDTO(Etudiant e) {
        return new EtudiantDTO(
                e.getId(),
                e.getNom(),
                e.getPrenom(),
                e.getParent().getId(),
                e.getClasse().getId()
        );
    }

    public Etudiant toEntity(EtudiantDTO dto) {
        Parent parent = parentRepository.findById(dto.getParentId()).orElseThrow(
                () -> new RuntimeException("Parent introuvable avec ID: " + dto.getParentId())
        );

        Classe classe = classeRepository.findById(dto.getClasseId()).orElseThrow(
                () -> new RuntimeException("Classe introuvable avec ID: " + dto.getClasseId())
        );

        return new Etudiant(
                dto.getId(),
                dto.getNom(),
                dto.getPrenom(),
                parent,
                classe
        );
    }
}
