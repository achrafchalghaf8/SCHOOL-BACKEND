package com.cscorner.services;

import com.cscorner.dto.AdminDTO;
import com.cscorner.entities.Admin;
import com.cscorner.entities.Compte;
import com.cscorner.mapper.AdminMapper;
import com.cscorner.repository.AdminRepository;
import com.cscorner.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private AdminMapper adminMapper;

    public AdminDTO createAdmin(AdminDTO dto) {
        if (adminRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        Admin admin = adminMapper.toEntity(dto);
        Admin savedAdmin = adminRepository.save(admin);

        // Vérification que l'Admin et le Compte ont bien été créés
        System.out.println("✅ Admin créé avec ID: " + savedAdmin.getId());
        System.out.println("✅ Compte associé créé avec ID: " + savedAdmin.getCompte().getId());
        System.out.println("✅ Email du compte: " + savedAdmin.getCompte().getEmail());
        System.out.println("✅ Les deux objets ont été créés avec succès !");

        return adminMapper.toDTO(savedAdmin);
    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AdminDTO getAdminById(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.map(adminMapper::toDTO).orElse(null);
    }

    public AdminDTO updateAdmin(Long id, AdminDTO dto) {
        if (adminRepository.existsById(id)) {
            Admin admin = adminMapper.toEntity(dto);
            admin.setId(id);
            return adminMapper.toDTO(adminRepository.save(admin));
        }
        return null;
    }

    public boolean deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Méthode utilitaire pour vérifier que l'Admin et le Compte correspondant existent
     */
    public boolean verifyAdminCompteSync(Long adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);

        if (admin.isPresent() && admin.get().getCompte() != null) {
            Long compteId = admin.get().getCompte().getId();
            Optional<Compte> compte = compteRepository.findById(compteId);

            if (compte.isPresent()) {
                System.out.println("✅ Admin et Compte synchronisés :");
                System.out.println("   - Admin ID: " + adminId);
                System.out.println("   - Compte ID: " + compteId);
                System.out.println("   - Admin email: " + admin.get().getEmail());
                System.out.println("   - Compte email: " + compte.get().getEmail());
                return true;
            }
        }

        System.out.println("❌ Désynchronisation détectée pour l'Admin ID: " + adminId);
        return false;
    }
}
