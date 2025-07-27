package com.cscorner.services;

import com.cscorner.dto.ParentDTO;
import com.cscorner.entities.Compte;
import com.cscorner.entities.Parent;
import com.cscorner.entities.Role;
import com.cscorner.mapper.ParentMapper;
import com.cscorner.repository.CompteRepository;
import com.cscorner.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ParentMapper parentMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public ParentDTO createParent(ParentDTO dto) {
        if (compteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Créer le compte
        Compte compte = new Compte();
        compte.setEmail(dto.getEmail());
        compte.setNom(dto.getNom());
        compte.setPassword(passwordEncoder.encode(dto.getPassword()));
        compte.setRole(Role.PARENT);

        // Créer le parent
        Parent parent = new Parent();
        parent.setTelephone(dto.getTelephone());
        parent.setCompte(compte);

        Parent savedParent = parentRepository.save(parent);
        return parentMapper.toDTO(savedParent);
    }

    public List<ParentDTO> getAllParents() {
        return parentRepository.findAll().stream()
                .map(parentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ParentDTO getParentById(Long id) {
        Optional<Parent> parent = parentRepository.findById(id);
        return parent.map(parentMapper::toDTO).orElse(null);
    }

    @Transactional
    public ParentDTO updateParent(Long id, ParentDTO dto) {
        Optional<Parent> parentOpt = parentRepository.findById(id);
        if (parentOpt.isPresent()) {
            Parent parent = parentOpt.get();
            
            // Mettre à jour les informations du parent
            parent.setTelephone(dto.getTelephone());
            
            // Mettre à jour les informations du compte associé
            Compte compte = parent.getCompte();
            if (compte != null) {
                compte.setEmail(dto.getEmail());
                compte.setNom(dto.getNom());
                
                // Ne mettre à jour le mot de passe que s'il est fourni
                if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                    compte.setPassword(passwordEncoder.encode(dto.getPassword()));
                }
                
                compte.setRole(Role.PARENT);
            }
            
            Parent updatedParent = parentRepository.save(parent);
            return parentMapper.toDTO(updatedParent);
        }
        return null;
    }

    @Transactional
    public boolean deleteParent(Long id) {
        try {
            if (parentRepository.existsById(id)) {
                // Supprimer d'abord les étudiants associés (relation OneToMany)
                // Cela sera géré automatiquement par JPA grâce à cascade = CascadeType.ALL
                
                // Supprimer le parent (le compte sera supprimé automatiquement grâce à la relation OneToOne)
                parentRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du parent " + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String verifyParentSync(Long id) {
        Optional<Parent> parentOpt = parentRepository.findById(id);
        if (parentOpt.isPresent()) {
            Parent parent = parentOpt.get();
            if (parent.getCompte() != null) {
                return "Parent et Compte sont synchronisés avec l'ID: " + id;
            }
        }
        return "Erreur: Parent ou Compte non trouvé pour l'ID: " + id;
    }
}