package com.cscorner.repository;

import com.cscorner.entities.Emploi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploiRepository extends JpaRepository<Emploi, Long> {
}
