package com.cscorner.controllers;

import com.cscorner.dto.EtudiantDTO;
import com.cscorner.services.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @PostMapping
    public ResponseEntity<EtudiantDTO> create(@RequestBody EtudiantDTO dto) {
        return new ResponseEntity<>(etudiantService.createEtudiant(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EtudiantDTO>> getAll() {
        return new ResponseEntity<>(etudiantService.getAllEtudiants(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtudiantDTO> getById(@PathVariable Long id) {
        EtudiantDTO dto = etudiantService.getEtudiantById(id);
        return dto != null ?
                new ResponseEntity<>(dto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtudiantDTO> update(@PathVariable Long id, @RequestBody EtudiantDTO dto) {
        EtudiantDTO updated = etudiantService.updateEtudiant(id, dto);
        return updated != null ?
                new ResponseEntity<>(updated, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return etudiantService.deleteEtudiant(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
