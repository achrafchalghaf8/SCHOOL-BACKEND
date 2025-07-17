package com.cscorner.controllers;

import com.cscorner.dto.CompteDTO;
import com.cscorner.dto.LoginRequest;
import com.cscorner.dto.LoginResponse;
import com.cscorner.entities.Compte;
import com.cscorner.repository.CompteRepository;
import com.cscorner.services.CompteService;
import com.cscorner.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {
    
    @Autowired
    private CompteService compteService;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CompteDTO compteDTO) {
        try {
            CompteDTO createdCompte = compteService.createCompte(compteDTO);
            return ResponseEntity.ok(createdCompte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<Compte> compteOpt = compteRepository.findByEmail(loginRequest.getEmail());
            
            if (compteOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), compteOpt.get().getPassword())) {
                Compte compte = compteOpt.get();

                // Générer un token pour l'utilisateur
                String token = tokenService.generateToken(compte);

                // Formater la date d'expiration
                String tokenExpiration = LocalDateTime.now().plusHours(24)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                LoginResponse response = new LoginResponse(
                    "success",
                    "Authentification réussie",
                    compte.getId(),
                    compte.getEmail(),
                    compte.getNom(),
                    compte.getRole(),
                    token,
                    tokenExpiration
                );

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'authentification: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Token manquant ou format invalide");
            }

            // Extraire le token du header Authorization
            String token = authHeader.substring(7); // Enlever "Bearer "

            // Valider le token avant de le supprimer
            TokenService.TokenInfo tokenInfo = tokenService.validateToken(token);
            if (tokenInfo == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide ou expiré");
            }

            // Invalider le token
            boolean success = tokenService.invalidateToken(token);

            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("message", "Déconnexion réussie");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body("Erreur lors de la déconnexion");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la déconnexion: " + e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token manquant ou format invalide");
            }

            // Extraire le token du header Authorization
            String token = authHeader.substring(7); // Enlever "Bearer "

            // Valider le token
            TokenService.TokenInfo tokenInfo = tokenService.validateToken(token);
            if (tokenInfo == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide ou expiré");
            }

            // Retourner les informations du token
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Token valide");
            response.put("userId", tokenInfo.getUserId());
            response.put("email", tokenInfo.getEmail());
            response.put("role", tokenInfo.getRole());
            response.put("expiration", tokenInfo.getExpirationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la validation: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Token manquant - utilisateur non authentifié");
                response.put("authenticated", false);
                return ResponseEntity.ok(response);
            }

            // Extraire le token du header Authorization
            String token = authHeader.substring(7); // Enlever "Bearer "

            // Valider le token
            TokenService.TokenInfo tokenInfo = tokenService.validateToken(token);
            if (tokenInfo == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Token invalide ou expiré");
                response.put("authenticated", false);
                return ResponseEntity.ok(response);
            }

            // Retourner les informations de l'utilisateur authentifié
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Utilisateur authentifié");
            response.put("authenticated", true);
            response.put("user", Map.of(
                "id", tokenInfo.getUserId(),
                "email", tokenInfo.getEmail(),
                "role", tokenInfo.getRole()
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la récupération des informations utilisateur: " + e.getMessage());
        }
    }
}