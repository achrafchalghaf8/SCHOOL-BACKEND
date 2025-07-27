package com.cscorner.mapper;

import com.cscorner.dto.CourDTO;
import com.cscorner.entities.Cour;
import com.cscorner.entities.Exercice;
import com.cscorner.repository.ExerciceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourMapper {

    @Autowired
    private ExerciceRepository exerciceRepository;

    public CourDTO toDTO(Cour cour) {
        List<Long> exerciceIds = cour.getExercices() == null
                ? List.of()
                : cour.getExercices().stream()
                    .map(Exercice::getId)
                    .collect(Collectors.toList());

        return new CourDTO(
                cour.getId(),
                cour.getFichier(),
                cour.getMatiere(),
                cour.getProprietaire(),
                exerciceIds
        );
    }

    public Cour toEntity(CourDTO dto) {
        List<Long> exerciceIds = dto.getExerciceIds();
        List<Exercice> exercices = (exerciceIds == null || exerciceIds.isEmpty())
                ? List.of()
                : exerciceRepository.findAllById(exerciceIds);

        return new Cour(
                dto.getId(),
                dto.getFichier(),
                dto.getMatiere(),
                dto.getProprietaire(),
                exercices
        );
    }
}
