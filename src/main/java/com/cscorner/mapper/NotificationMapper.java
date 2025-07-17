package com.cscorner.mapper;

import com.cscorner.dto.NotificationDTO;
import com.cscorner.entities.Compte;
import com.cscorner.entities.Notification;
import com.cscorner.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationMapper {

    @Autowired
    private CompteRepository compteRepository;

    public NotificationDTO toDTO(Notification notification) {
        List<Long> compteIds = notification.getComptes().stream()
                .map(Compte::getId)
                .collect(Collectors.toList());

        return new NotificationDTO(
                notification.getId(),
                notification.getContenu(),
                notification.getDate(),
                compteIds
        );
    }

    public Notification toEntity(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setContenu(dto.getContenu());
        notification.setDate(dto.getDate());
        
        if (dto.getCompteIds() != null) {
            List<Compte> comptes = compteRepository.findAllById(dto.getCompteIds());
            notification.setComptes(comptes);
            
            // Ajouter la notification à chaque compte
            for (Compte compte : comptes) {
                if (!compte.getNotifications().contains(notification)) {
                    compte.getNotifications().add(notification);
                }
            }
        }
        
        return notification;
    }
}