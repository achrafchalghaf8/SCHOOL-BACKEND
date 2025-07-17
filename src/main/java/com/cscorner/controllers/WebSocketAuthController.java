package com.cscorner.controllers;

import com.cscorner.dto.CompteDTO;
import com.cscorner.dto.LoginRequest;
import com.cscorner.dto.LoginResponse;
import com.cscorner.entities.Compte;
import com.cscorner.repository.CompteRepository;
import com.cscorner.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class WebSocketAuthController {

    @Autowired
    private CompteService compteService;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint pour l'enregistrement d'un nouveau compte via WebSocket
     */
    @MessageMapping("/auth/register")
    public void register(@Payload CompteDTO compteDTO, SimpMessageHeaderAccessor headerAccessor) {
        try {
            // Créer le nouveau compte
            CompteDTO createdCompte = compteService.createCompte(compteDTO);
            
            // Envoyer une réponse de succès
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Compte créé avec succès");
            response.put("compte", createdCompte);
            
            // Envoyer la réponse à l'utilisateur qui a fait la demande
            String sessionId = headerAccessor.getSessionId();
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/auth", response);
        } catch (Exception e) {
            // Envoyer une réponse d'erreur
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            
            // Envoyer la réponse à l'utilisateur qui a fait la demande
            String sessionId = headerAccessor.getSessionId();
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/auth", response);
        }
    }

    /**
     * Endpoint pour l'authentification via WebSocket
     */
    @MessageMapping("/auth/login")
    public void login(@Payload LoginRequest loginRequest, SimpMessageHeaderAccessor headerAccessor) {
        try {
            // Vérifier les informations d'authentification
            Optional<Compte> compteOpt = compteRepository.findByEmail(loginRequest.getEmail());
            
            if (compteOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), compteOpt.get().getPassword())) {
                // Authentification réussie
                Compte compte = compteOpt.get();
                
                // Stocker les informations d'authentification dans les attributs de session
                headerAccessor.getSessionAttributes().put("authenticated", true);
                headerAccessor.getSessionAttributes().put("compteId", compte.getId());
                
                // Créer la réponse (WebSocket n'utilise pas de token pour le moment)
                LoginResponse response = new LoginResponse(
                    "success",
                    "Authentification réussie",
                    compte.getId(),
                    compte.getEmail(),
                    compte.getNom(),
                    compte.getRole(),
                    null, // Pas de token pour WebSocket
                    null  // Pas d'expiration pour WebSocket
                );
                
                // Envoyer la réponse à l'utilisateur
                String sessionId = headerAccessor.getSessionId();
                messagingTemplate.convertAndSendToUser(sessionId, "/queue/auth", response);
            } else {
                // Authentification échouée
                Map<String, Object> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Email ou mot de passe incorrect");
                
                // Envoyer la réponse à l'utilisateur
                String sessionId = headerAccessor.getSessionId();
                messagingTemplate.convertAndSendToUser(sessionId, "/queue/auth", response);
            }
        } catch (Exception e) {
            // Erreur lors de l'authentification
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Erreur lors de l'authentification: " + e.getMessage());
            
            // Envoyer la réponse à l'utilisateur
            String sessionId = headerAccessor.getSessionId();
            messagingTemplate.convertAndSendToUser(sessionId, "/queue/auth", response);
        }
    }
}