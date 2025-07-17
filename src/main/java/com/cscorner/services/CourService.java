package com.cscorner.services;

import com.cscorner.dto.CourDTO;
import com.cscorner.entities.Cour;
import com.cscorner.mapper.CourMapper;
import com.cscorner.repository.CourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourService {

    @Autowired
    private CourRepository courRepository;

    @Autowired
    private CourMapper courMapper;

    public CourDTO createCour(CourDTO dto) {
        Cour cour = courMapper.toEntity(dto);
        return courMapper.toDTO(courRepository.save(cour));
    }

    public List<CourDTO> getAllCours() {
        return courRepository.findAll().stream()
                .map(courMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourDTO getCourById(Long id) {
        Optional<Cour> cour = courRepository.findById(id);
        return cour.map(courMapper::toDTO).orElse(null);
    }

    public CourDTO updateCour(Long id, CourDTO dto) {
        if (courRepository.existsById(id)) {
            Cour cour = courMapper.toEntity(dto);
            cour.setId(id);
            return courMapper.toDTO(courRepository.save(cour));
        }
        return null;
    }

    public boolean deleteCour(Long id) {
        if (courRepository.existsById(id)) {
            courRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
