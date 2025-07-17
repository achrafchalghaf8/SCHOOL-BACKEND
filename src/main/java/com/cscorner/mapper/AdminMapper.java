package com.cscorner.mapper;

import com.cscorner.dto.AdminDTO;
import com.cscorner.entities.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDTO toDTO(Admin admin) {
        return new AdminDTO(
                admin.getId(),
                admin.getEmail(),
                admin.getNom(),
                admin.getPassword()
        );
    }

    public Admin toEntity(AdminDTO dto) {
        if (dto.getId() != null) {
            // Pour les mises à jour
            return new Admin(
                    dto.getId(),
                    dto.getEmail(),
                    dto.getNom(),
                    dto.getPassword()
            );
        } else {
            // Pour les nouvelles créations
            return new Admin(
                    dto.getEmail(),
                    dto.getNom(),
                    dto.getPassword()
            );
        }
    }
}
