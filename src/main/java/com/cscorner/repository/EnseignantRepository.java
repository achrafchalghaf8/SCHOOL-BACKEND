package com.cscorner.repository;

import com.cscorner.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Enseignant e WHERE e.compte.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT e FROM Enseignant e WHERE e.compte.email = :email")
    Enseignant findByEmail(@Param("email") String email);
}