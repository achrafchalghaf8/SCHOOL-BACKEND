package com.cscorner.services;

import com.cscorner.dto.ExerciceDTO;
import com.cscorner.entities.Exercice;
import com.cscorner.mapper.ExerciceMapper;
import com.cscorner.repository.ExerciceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciceService {

    @Autowired
    private ExerciceRepository exerciceRepository;

    @Autowired
    private ExerciceMapper exerciceMapper;

    public ExerciceDTO createExercice(ExerciceDTO dto) {
        if (dto.getDatePublication() == null) {
            dto.setDatePublication(new Date()); // Défaut : date actuelle si null
        }
        Exercice exercice = exerciceMapper.toEntity(dto);
        return exerciceMapper.toDTO(exerciceRepository.save(exercice));
    }

    public List<ExerciceDTO> getAllExercices() {
        return exerciceRepository.findAll().stream()
                .map(exerciceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ExerciceDTO getExerciceById(Long id) {
        Optional<Exercice> exercice = exerciceRepository.findById(id);
        return exercice.map(exerciceMapper::toDTO).orElse(null);
    }

    public ExerciceDTO updateExercice(Long id, ExerciceDTO dto) {
        if (exerciceRepository.existsById(id)) {
            if (dto.getDatePublication() == null) {
                dto.setDatePublication(new Date()); // Défaut date actuelle si null
            }
            Exercice exercice = exerciceMapper.toEntity(dto);
            exercice.setId(id);
            return exerciceMapper.toDTO(exerciceRepository.save(exercice));
        }
        return null;
    }

    public boolean deleteExercice(Long id) {
        if (exerciceRepository.existsById(id)) {
            exerciceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
