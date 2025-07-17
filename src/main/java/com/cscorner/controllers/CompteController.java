package com.cscorner.controllers;

import com.cscorner.dto.CompteDTO;
import com.cscorner.services.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    private final CompteService compteService;

    // Explicit constructor instead of Lombok
    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @PostMapping
    public ResponseEntity<CompteDTO> createCompte(@RequestBody CompteDTO compteDTO) {
        CompteDTO createdCompte = compteService.createCompte(compteDTO);
        return new ResponseEntity<>(createdCompte, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CompteDTO>> getAllComptes() {
        List<CompteDTO> comptes = compteService.getAllComptes();
        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteDTO> getCompteById(@PathVariable Long id) {
        CompteDTO compte = compteService.getCompteById(id);
        if (compte != null) {
            return new ResponseEntity<>(compte, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompteDTO> updateCompte(@PathVariable Long id, @RequestBody CompteDTO compteDTO) {
        CompteDTO updatedCompte = compteService.updateCompte(id, compteDTO);
        if (updatedCompte != null) {
            return new ResponseEntity<>(updatedCompte, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        if (compteService.deleteCompte(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}