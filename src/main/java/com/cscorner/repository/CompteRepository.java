package com.cscorner.repository;

import com.cscorner.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
        Optional<Compte> findByEmail(String email);
    boolean existsByEmail(String email);
}
