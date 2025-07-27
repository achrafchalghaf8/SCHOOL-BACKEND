package com.cscorner.services;

import com.cscorner.dto.ClasseDTO;
import com.cscorner.dto.EnseignantDTO;
import com.cscorner.entities.Classe;
import com.cscorner.entities.Enseignant;
import com.cscorner.mapper.ClasseMapper;
import com.cscorner.repository.ClasseRepository;
import com.cscorner.repository.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private ClasseMapper classeMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public ClasseDTO createClasse(ClasseDTO dto) {
        Classe classe = classeMapper.toEntity(dto);
        
        // Gérer les associations avec les enseignants
        if (dto.getEnseignantIds() != null && !dto.getEnseignantIds().isEmpty()) {
            List<Enseignant> enseignants = enseignantRepository.findAllById(dto.getEnseignantIds());
            classe.setEnseignants(enseignants);
        }
        
        return classeMapper.toDTO(classeRepository.save(classe));
    }

    public List<ClasseDTO> getAllClasses() {
        return classeRepository.findAll().stream()
                .map(classeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClasseDTO getClasseById(Long id) {
        Optional<Classe> classe = classeRepository.findById(id);
        return classe.map(classeMapper::toDTO).orElse(null);
    }

    @Transactional
    public ClasseDTO updateClasse(Long id, ClasseDTO dto) {
        if (classeRepository.existsById(id)) {
            Classe updated = classeMapper.toEntity(dto);
            updated.setId(id);
            
            // Mettre à jour les associations avec les enseignants
            if (dto.getEnseignantIds() != null) {
                List<Enseignant> enseignants = enseignantRepository.findAllById(dto.getEnseignantIds());
                updated.getEnseignants().clear();
                updated.getEnseignants().addAll(enseignants);
            }
            
            return classeMapper.toDTO(classeRepository.save(updated));
        }
        return null;
    }

    @Transactional
    public boolean deleteClasse(Long id) {
        try {
            if (classeRepository.existsById(id)) {
                // Supprimer d'abord les associations ManyToMany
                jdbcTemplate.update("DELETE FROM enseignant_classe WHERE classe_id = ?", id);
                jdbcTemplate.update("DELETE FROM exercice_classes WHERE classe_id = ?", id);
                
                // Maintenant supprimer la classe (les relations OneToMany seront supprimées automatiquement)
                classeRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de la classe " + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<EnseignantDTO> getEnseignantsByClasseId(Long classeId) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new RuntimeException("Classe not found"));
        
        return classe.getEnseignants().stream()
                .map(enseignant -> {
                    EnseignantDTO dto = new EnseignantDTO();
                    dto.setId(enseignant.getId());
                    dto.setNom(enseignant.getNom());
                    dto.setEmail(enseignant.getEmail());
                    dto.setSpecialite(enseignant.getSpecialite());
                    dto.setTelephone(enseignant.getTelephone());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}