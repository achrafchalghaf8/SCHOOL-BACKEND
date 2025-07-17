package com.cscorner.dto;

import com.cscorner.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String status;
    private String message;
    private Long id;
    private String email;
    private String nom;
    private Role role;
    private String token;
    private String tokenExpiration;
}
