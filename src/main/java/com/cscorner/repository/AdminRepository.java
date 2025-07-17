package com.cscorner.repository;

import com.cscorner.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Admin a WHERE a.compte.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT a FROM Admin a WHERE a.compte.email = :email")
    Admin findByEmail(@Param("email") String email);
}
