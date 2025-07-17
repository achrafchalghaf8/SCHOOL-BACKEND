package com.cscorner.services;

import com.cscorner.dto.NotificationDTO;
import com.cscorner.entities.Compte;
import com.cscorner.entities.Notification;
import com.cscorner.mapper.NotificationMapper;
import com.cscorner.repository.CompteRepository;
import com.cscorner.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    public NotificationDTO createNotification(NotificationDTO dto) {
        Notification notification = notificationMapper.toEntity(dto);
        Notification savedNotification = notificationRepository.save(notification);
        
        if (dto.getCompteIds() != null && !dto.getCompteIds().isEmpty()) {
            List<Compte> comptes = compteRepository.findAllById(dto.getCompteIds());
            for (Compte compte : comptes) {
                compteRepository.save(compte); // Cela sauvegarde aussi la relation
            }
        }
        
        return notificationMapper.toDTO(savedNotification);
    }

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public NotificationDTO getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.map(notificationMapper::toDTO).orElse(null);
    }

    public NotificationDTO updateNotification(Long id, NotificationDTO dto) {
        if (notificationRepository.existsById(id)) {
            // Charger la notification existante avec ses relations
            Notification existingNotification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
            
            // Supprimer les anciennes relations
            for (Compte compte : existingNotification.getComptes()) {
                compte.getNotifications().remove(existingNotification);
                compteRepository.save(compte);
            }
            
            // Mettre à jour la notification
            Notification notification = notificationMapper.toEntity(dto);
            notification.setId(id);
            Notification updatedNotification = notificationRepository.save(notification);
            
            // Sauvegarder les nouvelles relations
            if (dto.getCompteIds() != null && !dto.getCompteIds().isEmpty()) {
                List<Compte> comptes = compteRepository.findAllById(dto.getCompteIds());
                for (Compte compte : comptes) {
                    compteRepository.save(compte);
                }
            }
            
            return notificationMapper.toDTO(updatedNotification);
        }
        return null;
    }

    public boolean deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            Notification notification = notificationRepository.findById(id).orElseThrow();
            
            // Supprimer les relations avant de supprimer la notification
            for (Compte compte : notification.getComptes()) {
                compte.getNotifications().remove(notification);
                compteRepository.save(compte);
            }
            
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}