package com.cscorner.controllers;

import com.cscorner.dto.EmploiDTO;
import com.cscorner.services.EmploiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emplois")
public class EmploiController {

    @Autowired
    private EmploiService emploiService;

   @PostMapping
    public ResponseEntity<?> create(@RequestBody EmploiDTO dto) {
        try {
            // Calculer la taille réelle du fichier (base64 → binaire)
            int base64Length = dto.getFichier().length();
            int padding = 0;
            if (dto.getFichier().endsWith("==")) {
                padding = 2;
            } else if (dto.getFichier().endsWith("=")) {
                padding = 1;
            }
            int fileSize = (base64Length * 3) / 4 - padding;
            // Valider la taille (max 25 MB en binaire)
            if (fileSize > 25 * 1024 * 1024) {
                return ResponseEntity
                    .status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("Fichier trop volumineux (max 25MB binaire, soit ~33MB base64)");
            }
            return new ResponseEntity<>(emploiService.createEmploi(dto), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur de traitement: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<EmploiDTO>> getAll() {
        return new ResponseEntity<>(emploiService.getAllEmplois(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmploiDTO> getById(@PathVariable Long id) {
        EmploiDTO dto = emploiService.getEmploiById(id);
        return dto != null ?
                new ResponseEntity<>(dto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmploiDTO> update(@PathVariable Long id, @RequestBody EmploiDTO dto) {
        try {
            int base64Length = dto.getFichier().length();
            int padding = 0;
            if (dto.getFichier().endsWith("==")) {
                padding = 2;
            } else if (dto.getFichier().endsWith("=")) {
                padding = 1;
            }
            int fileSize = (base64Length * 3) / 4 - padding;
            if (fileSize > 25 * 1024 * 1024) {
                return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
            }
            EmploiDTO updated = emploiService.updateEmploi(id, dto);
            return updated != null ?
                    new ResponseEntity<>(updated, HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return emploiService.deleteEmploi(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
