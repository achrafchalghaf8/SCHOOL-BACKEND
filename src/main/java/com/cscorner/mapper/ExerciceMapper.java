package com.cscorner.mapper;

import com.cscorner.dto.ExerciceDTO;
import com.cscorner.entities.Classe;
import com.cscorner.entities.Cour;
import com.cscorner.entities.Exercice;
import com.cscorner.repository.ClasseRepository;
import com.cscorner.repository.CourRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExerciceMapper {

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private CourRepository courRepository;

    public ExerciceDTO toDTO(Exercice exercice) {
        List<Long> classeIds = exercice.getClasses().stream()
                .map(Classe::getId)
                .collect(Collectors.toList());

        Long courId = exercice.getCour() != null ? exercice.getCour().getId() : null;

        return new ExerciceDTO(
                exercice.getId(),
                exercice.getContenu(),
                exercice.getDatePublication(),
                exercice.getFichier(),
                classeIds,
                courId
        );
    }

    public Exercice toEntity(ExerciceDTO dto) {
        List<Long> classeIds = dto.getClasseIds();
        List<Classe> classes = (classeIds == null || classeIds.isEmpty())
                ? List.of()
                : classeRepository.findAllById(classeIds);

        Cour cour = null;
        if (dto.getCourId() != null) {
            cour = courRepository.findById(dto.getCourId()).orElse(null);
        }

        return new Exercice(
                dto.getId(),
                dto.getContenu(),
                dto.getDatePublication(),
                dto.getFichier(),
                classes,
                cour
        );
    }
}
