package com.cscorner.config;

import com.cscorner.services.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TokenAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    // Liste des endpoints publics qui ne nécessitent pas d'authentification
    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
        "/api/auth/login",
        "/api/auth/register",
        "/api/auth/validate",
        "/ws",
        "/error"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        // Permettre les requêtes OPTIONS (CORS preflight)
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        String requestURI = request.getRequestURI();
        
        // Vérifier si l'endpoint est public
        if (isPublicEndpoint(requestURI)) {
            return true;
        }
        
        // Extraire le token du header Authorization
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response, "Token manquant ou format invalide");
            return false;
        }
        
        String token = authHeader.substring(7); // Enlever "Bearer "
        
        // Valider le token
        TokenService.TokenInfo tokenInfo = tokenService.validateToken(token);
        
        if (tokenInfo == null) {
            sendUnauthorizedResponse(response, "Token invalide ou expiré");
            return false;
        }
        
        // Ajouter les informations de l'utilisateur aux attributs de la requête
        request.setAttribute("userId", tokenInfo.getUserId());
        request.setAttribute("userEmail", tokenInfo.getEmail());
        request.setAttribute("userRole", tokenInfo.getRole());
        
        return true;
    }
    
    /**
     * Vérifie si un endpoint est public
     */
    private boolean isPublicEndpoint(String requestURI) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(requestURI::startsWith);
    }
    
    /**
     * Envoie une réponse d'erreur 401
     */
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("message", message);
        errorResponse.put("code", 401);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        
        response.getWriter().write(jsonResponse);
    }
}
