package com.cscorner.services;

import com.cscorner.dto.EmploiDTO;
import com.cscorner.entities.Emploi;
import com.cscorner.mapper.EmploiMapper;
import com.cscorner.repository.EmploiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmploiService {

    @Autowired
    private EmploiRepository emploiRepository;

    @Autowired
    private EmploiMapper emploiMapper;

    public EmploiDTO createEmploi(EmploiDTO dto) {
        Emploi emploi = emploiMapper.toEntity(dto);
        return emploiMapper.toDTO(emploiRepository.save(emploi));
    }

    public List<EmploiDTO> getAllEmplois() {
        return emploiRepository.findAll().stream()
                .map(emploiMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EmploiDTO getEmploiById(Long id) {
        Optional<Emploi> emploi = emploiRepository.findById(id);
        return emploi.map(emploiMapper::toDTO).orElse(null);
    }

    public EmploiDTO updateEmploi(Long id, EmploiDTO dto) {
        if (emploiRepository.existsById(id)) {
            Emploi emploi = emploiMapper.toEntity(dto);
            emploi.setId(id);
            return emploiMapper.toDTO(emploiRepository.save(emploi));
        }
        return null;
    }

    public boolean deleteEmploi(Long id) {
        if (emploiRepository.existsById(id)) {
            emploiRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
