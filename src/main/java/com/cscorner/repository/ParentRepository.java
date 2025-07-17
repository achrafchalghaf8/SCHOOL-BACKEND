package com.cscorner.repository;

import com.cscorner.entities.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Parent p WHERE p.compte.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT p FROM Parent p WHERE p.compte.email = :email")
    Parent findByEmail(@Param("email") String email);
}
