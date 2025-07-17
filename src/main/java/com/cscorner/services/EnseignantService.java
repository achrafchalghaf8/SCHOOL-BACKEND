package com.cscorner.services;

import com.cscorner.dto.ClasseDTO;
import com.cscorner.dto.EnseignantDTO;
import com.cscorner.entities.Classe;
import com.cscorner.entities.Compte;
import com.cscorner.entities.Enseignant;
import com.cscorner.entities.Role;
import com.cscorner.mapper.EnseignantMapper;
import com.cscorner.repository.ClasseRepository;
import com.cscorner.repository.CompteRepository;
import com.cscorner.repository.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private EnseignantMapper enseignantMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public EnseignantDTO createEnseignant(EnseignantDTO dto) {
        if (compteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Créer le compte
        Compte compte = new Compte();
        compte.setEmail(dto.getEmail());
        compte.setNom(dto.getNom());
        compte.setPassword(passwordEncoder.encode(dto.getPassword()));
        compte.setRole(Role.ENSEIGNANT);

        // Créer l'enseignant
        Enseignant enseignant = new Enseignant();
        enseignant.setSpecialite(dto.getSpecialite());
        enseignant.setTelephone(dto.getTelephone());
        enseignant.setCompte(compte);

        // Gérer les associations avec les classes
        if (dto.getClasseIds() != null && !dto.getClasseIds().isEmpty()) {
            List<Classe> classes = classeRepository.findAllById(dto.getClasseIds());
            enseignant.setClasses(classes);
        }

        Enseignant savedEnseignant = enseignantRepository.save(enseignant);
        return enseignantMapper.toDTO(savedEnseignant);
    }

    public List<EnseignantDTO> getAllEnseignants() {
        return enseignantRepository.findAll().stream()
                .map(enseignantMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EnseignantDTO getEnseignantById(Long id) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(id);
        return enseignant.map(enseignantMapper::toDTO).orElse(null);
    }

    @Transactional
    public EnseignantDTO updateEnseignant(Long id, EnseignantDTO dto) {
        Optional<Enseignant> enseignantOpt = enseignantRepository.findById(id);
        if (enseignantOpt.isPresent()) {
            Enseignant enseignant = enseignantOpt.get();
            
            // Mettre à jour les informations de l'enseignant
            enseignant.setSpecialite(dto.getSpecialite());
            enseignant.setTelephone(dto.getTelephone());
            
            // Mettre à jour les informations du compte associé
            Compte compte = enseignant.getCompte();
            if (compte != null) {
                compte.setEmail(dto.getEmail());
                compte.setNom(dto.getNom());
                
                if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                    compte.setPassword(passwordEncoder.encode(dto.getPassword()));
                }
                
                compte.setRole(Role.ENSEIGNANT);
            }
            
            // Mettre à jour les associations avec les classes
            if (dto.getClasseIds() != null) {
                List<Classe> classes = classeRepository.findAllById(dto.getClasseIds());
                enseignant.getClasses().clear();
                enseignant.getClasses().addAll(classes);
            }
            
            Enseignant updatedEnseignant = enseignantRepository.save(enseignant);
            return enseignantMapper.toDTO(updatedEnseignant);
        }
        return null;
    }

    @Transactional
    public boolean deleteEnseignant(Long id) {
        if (enseignantRepository.existsById(id)) {
            enseignantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public String verifyEnseignantSync(Long id) {
        Optional<Enseignant> enseignantOpt = enseignantRepository.findById(id);
        if (enseignantOpt.isPresent()) {
            Enseignant enseignant = enseignantOpt.get();
            if (enseignant.getCompte() != null) {
                return "Enseignant et Compte sont synchronisés avec l'ID: " + id;
            }
        }
        return "Erreur: Enseignant ou Compte non trouvé pour l'ID: " + id;
    }

    @Transactional
    public void addClasseToEnseignant(Long enseignantId, Long classeId) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant not found"));
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new RuntimeException("Classe not found"));

        if (!enseignant.getClasses().contains(classe)) {
            enseignant.getClasses().add(classe);
            classe.getEnseignants().add(enseignant);
            enseignantRepository.save(enseignant);
            classeRepository.save(classe);
        }
    }

    @Transactional
    public void removeClasseFromEnseignant(Long enseignantId, Long classeId) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant not found"));
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new RuntimeException("Classe not found"));

        enseignant.getClasses().remove(classe);
        classe.getEnseignants().remove(enseignant);
        enseignantRepository.save(enseignant);
        classeRepository.save(classe);
    }

    public List<ClasseDTO> getClassesByEnseignantId(Long enseignantId) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant not found"));
        
        return enseignant.getClasses().stream()
                .map(classe -> new ClasseDTO(classe.getId(), classe.getNiveau()))
                .collect(Collectors.toList());
    }
}