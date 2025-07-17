package com.cscorner.config;

import com.cscorner.entities.Compte;
import com.cscorner.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Autowired
    private CompteRepository compteRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Map pour stocker les sessions authentifiées (sessionId -> compteId)
    private final Map<String, Long> authenticatedSessions = new ConcurrentHashMap<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        if (accessor != null) {
            // Gestion de la connexion STOMP
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                // Récupérer les informations d'authentification des headers
                List<String> emailHeaders = accessor.getNativeHeader("email");
                List<String> passwordHeaders = accessor.getNativeHeader("password");
                
                if (emailHeaders != null && passwordHeaders != null) {
                    String email = emailHeaders.get(0);
                    String password = passwordHeaders.get(0);
                    
                    // Vérifier les informations d'authentification
                    Optional<Compte> compteOpt = compteRepository.findByEmail(email);
                    if (compteOpt.isPresent() && passwordEncoder.matches(password, compteOpt.get().getPassword())) {
                        // Authentification réussie, stocker la session
                        String sessionId = accessor.getSessionId();
                        authenticatedSessions.put(sessionId, compteOpt.get().getId());
                    } else {
                        // Authentification échouée
                        throw new IllegalArgumentException("Invalid credentials");
                    }
                }
            } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                // Nettoyer la session lors de la déconnexion
                String sessionId = accessor.getSessionId();
                if (sessionId != null) {
                    authenticatedSessions.remove(sessionId);
                }
            } else {
                // Pour les autres commandes, vérifier si la session est authentifiée
                String sessionId = accessor.getSessionId();
                if (sessionId != null && !authenticatedSessions.containsKey(sessionId)) {
                    // Exclure les endpoints publics (comme /app/register)
                    String destination = accessor.getDestination();
                    if (destination != null && 
                        !destination.startsWith("/app/auth/register") && 
                        !destination.startsWith("/app/auth/login")) {
                        throw new IllegalStateException("Not authenticated");
                    }
                }
            }
        }
        
        return message;
    }
    
    // Méthode pour vérifier si une session est authentifiée
    public boolean isSessionAuthenticated(String sessionId) {
        return authenticatedSessions.containsKey(sessionId);
    }
    
    // Méthode pour récupérer l'ID du compte associé à une session
    public Long getCompteIdFromSession(String sessionId) {
        return authenticatedSessions.get(sessionId);
    }
}