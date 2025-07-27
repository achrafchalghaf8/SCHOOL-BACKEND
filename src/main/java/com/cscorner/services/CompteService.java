package com.cscorner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import com.cscorner.dto.CompteDTO;
import com.cscorner.entities.Compte;
import com.cscorner.mapper.CompteMapper;
import com.cscorner.repository.CompteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompteService {
    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteMapper compteMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CompteDTO createCompte(CompteDTO compteDTO) {
        if (compteRepository.existsByEmail(compteDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Compte compte = compteMapper.toEntity(compteDTO);
        
        // Encoder le mot de passe avant de sauvegarder
        compte.setPassword(passwordEncoder.encode(compte.getPassword()));
        
        compte = compteRepository.save(compte);
        return compteMapper.toDTO(compte);
    }

    public List<CompteDTO> getAllComptes() {
        return compteRepository.findAll().stream()
                .map(compteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CompteDTO getCompteById(Long id) {
        Optional<Compte> compte = compteRepository.findById(id);
        return compte.map(compteMapper::toDTO).orElse(null);
    }

    public CompteDTO updateCompte(Long id, CompteDTO compteDTO) {
        if (compteRepository.existsById(id)) {
            Compte compte = compteMapper.toEntity(compteDTO);
            compte.setId(id);
            
            // Encoder le mot de passe avant de sauvegarder (comme dans createCompte)
            compte.setPassword(passwordEncoder.encode(compte.getPassword()));
            
            compte = compteRepository.save(compte);
            return compteMapper.toDTO(compte);
        }
        return null;
    }

    @Transactional
    public boolean deleteCompte(Long id) {
        try {
            if (compteRepository.existsById(id)) {
                // Supprimer d'abord les associations ManyToMany avec les notifications
                jdbcTemplate.update("DELETE FROM compte_notifications WHERE compte_id = ?", id);
                
                // Maintenant supprimer le compte
                // Les relations OneToOne (Admin, Parent, Enseignant) seront supprimées automatiquement
                // grâce à cascade = CascadeType.ALL et orphanRemoval = true
                compteRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du compte " + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}