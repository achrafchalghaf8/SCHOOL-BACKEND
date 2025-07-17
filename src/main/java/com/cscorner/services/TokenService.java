package com.cscorner.services;

import com.cscorner.entities.Compte;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    
    // Stockage des tokens en mémoire (pour la production, utiliser Redis ou une base de données)
    private final Map<String, TokenInfo> activeTokens = new ConcurrentHashMap<>();
    
    // Durée de validité des tokens en heures
    private static final int TOKEN_VALIDITY_HOURS = 24;
    
    /**
     * Génère un nouveau token pour un utilisateur
     */
    public String generateToken(Compte compte) {
        // Générer un token aléatoirement sécurisé
        String token = generateSecureToken();
        
        // Créer les informations du token
        TokenInfo tokenInfo = new TokenInfo(
            compte.getId(),
            compte.getEmail(),
            compte.getRole().toString(),
            LocalDateTime.now().plusHours(TOKEN_VALIDITY_HOURS)
        );
        
        // Stocker le token
        activeTokens.put(token, tokenInfo);
        
        // Nettoyer les tokens expirés
        cleanExpiredTokens();
        
        return token;
    }
    
    /**
     * Valide un token et retourne les informations associées
     */
    public TokenInfo validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return null;
        }
        
        TokenInfo tokenInfo = activeTokens.get(token);
        
        if (tokenInfo == null) {
            return null; // Token inexistant
        }
        
        if (tokenInfo.getExpirationTime().isBefore(LocalDateTime.now())) {
            // Token expiré, le supprimer
            activeTokens.remove(token);
            return null;
        }
        
        return tokenInfo;
    }
    
    /**
     * Invalide un token (logout)
     */
    public boolean invalidateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        
        return activeTokens.remove(token) != null;
    }
    
    /**
     * Invalide tous les tokens d'un utilisateur
     */
    public void invalidateAllUserTokens(Long userId) {
        activeTokens.entrySet().removeIf(entry -> 
            entry.getValue().getUserId().equals(userId)
        );
    }
    
    /**
     * Génère un token sécurisé
     */
    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    
    /**
     * Nettoie les tokens expirés
     */
    private void cleanExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        activeTokens.entrySet().removeIf(entry -> 
            entry.getValue().getExpirationTime().isBefore(now)
        );
    }
    
    /**
     * Retourne le nombre de tokens actifs (pour debug)
     */
    public int getActiveTokensCount() {
        cleanExpiredTokens();
        return activeTokens.size();
    }
    
    /**
     * Classe interne pour stocker les informations du token
     */
    public static class TokenInfo {
        private final Long userId;
        private final String email;
        private final String role;
        private final LocalDateTime expirationTime;
        
        public TokenInfo(Long userId, String email, String role, LocalDateTime expirationTime) {
            this.userId = userId;
            this.email = email;
            this.role = role;
            this.expirationTime = expirationTime;
        }
        
        // Getters
        public Long getUserId() { return userId; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
        public LocalDateTime getExpirationTime() { return expirationTime; }
    }
}
