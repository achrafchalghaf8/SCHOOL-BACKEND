package com.cscorner.services;

import com.cscorner.dto.EtudiantDTO;
import com.cscorner.entities.Etudiant;
import com.cscorner.mapper.EtudiantMapper;
import com.cscorner.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private EtudiantMapper etudiantMapper;

    public EtudiantDTO createEtudiant(EtudiantDTO dto) {
        Etudiant etudiant = etudiantMapper.toEntity(dto);
        return etudiantMapper.toDTO(etudiantRepository.save(etudiant));
    }

    public List<EtudiantDTO> getAllEtudiants() {
        return etudiantRepository.findAll().stream()
                .map(etudiantMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EtudiantDTO getEtudiantById(Long id) {
        Optional<Etudiant> etudiant = etudiantRepository.findById(id);
        return etudiant.map(etudiantMapper::toDTO).orElse(null);
    }

    public EtudiantDTO updateEtudiant(Long id, EtudiantDTO dto) {
        if (etudiantRepository.existsById(id)) {
            Etudiant etudiant = etudiantMapper.toEntity(dto);
            etudiant.setId(id);
            return etudiantMapper.toDTO(etudiantRepository.save(etudiant));
        }
        return null;
    }

    public boolean deleteEtudiant(Long id) {
        if (etudiantRepository.existsById(id)) {
            etudiantRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
