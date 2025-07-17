package com.cscorner.controllers;

import com.cscorner.dto.ClasseDTO;
import com.cscorner.dto.EnseignantDTO;
import com.cscorner.services.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @PostMapping
    public ResponseEntity<EnseignantDTO> createEnseignant(@RequestBody EnseignantDTO dto) {
        try {
            EnseignantDTO createdEnseignant = enseignantService.createEnseignant(dto);
            return new ResponseEntity<>(createdEnseignant, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<EnseignantDTO>> getAllEnseignants() {
        List<EnseignantDTO> enseignants = enseignantService.getAllEnseignants();
        return new ResponseEntity<>(enseignants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDTO> getEnseignantById(@PathVariable Long id) {
        EnseignantDTO enseignant = enseignantService.getEnseignantById(id);
        return enseignant != null ? 
                new ResponseEntity<>(enseignant, HttpStatus.OK) : 
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnseignantDTO> updateEnseignant(@PathVariable Long id, @RequestBody EnseignantDTO dto) {
        try {
            EnseignantDTO updatedEnseignant = enseignantService.updateEnseignant(id, dto);
            return updatedEnseignant != null ? 
                    new ResponseEntity<>(updatedEnseignant, HttpStatus.OK) : 
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        boolean deleted = enseignantService.deleteEnseignant(id);
        return deleted ? 
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : 
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/verify-sync")
    public ResponseEntity<String> verifyEnseignantSync(@PathVariable Long id) {
        String result = enseignantService.verifyEnseignantSync(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}/classes")
    public ResponseEntity<List<ClasseDTO>> getClassesByEnseignant(@PathVariable Long id) {
        List<ClasseDTO> classes = enseignantService.getClassesByEnseignantId(id);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @PostMapping("/{enseignantId}/classes/{classeId}")
    public ResponseEntity<Void> addClasseToEnseignant(
            @PathVariable Long enseignantId,
            @PathVariable Long classeId) {
        try {
            enseignantService.addClasseToEnseignant(enseignantId, classeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{enseignantId}/classes/{classeId}")
    public ResponseEntity<Void> removeClasseFromEnseignant(
            @PathVariable Long enseignantId,
            @PathVariable Long classeId) {
        try {
            enseignantService.removeClasseFromEnseignant(enseignantId, classeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}