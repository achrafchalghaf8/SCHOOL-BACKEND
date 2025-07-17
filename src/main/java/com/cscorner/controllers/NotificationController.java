package com.cscorner.controllers;

import com.cscorner.dto.NotificationDTO;
import com.cscorner.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public NotificationDTO create(@RequestBody NotificationDTO dto) {
        return notificationService.createNotification(dto);
    }

    @GetMapping
    public List<NotificationDTO> getAll() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public NotificationDTO getById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @PutMapping("/{id}")
    public NotificationDTO update(@PathVariable Long id, @RequestBody NotificationDTO dto) {
        return notificationService.updateNotification(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
    
    
}
