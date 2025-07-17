package com.cscorner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
            compte = compteRepository.save(compte);
            return compteMapper.toDTO(compte);
        }
        return null;
    }

    public boolean deleteCompte(Long id) {
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            return true;
        }
        return false;
    }

}