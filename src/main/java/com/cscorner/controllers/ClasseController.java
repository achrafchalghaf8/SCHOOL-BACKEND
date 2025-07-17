package com.cscorner.controllers;

import com.cscorner.dto.ClasseDTO;
import com.cscorner.dto.EnseignantDTO;
import com.cscorner.services.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @PostMapping
    public ResponseEntity<ClasseDTO> create(@RequestBody ClasseDTO dto) {
        return new ResponseEntity<>(classeService.createClasse(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClasseDTO>> getAll() {
        return new ResponseEntity<>(classeService.getAllClasses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseDTO> getById(@PathVariable Long id) {
        ClasseDTO dto = classeService.getClasseById(id);
        return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClasseDTO> update(@PathVariable Long id, @RequestBody ClasseDTO dto) {
        ClasseDTO updated = classeService.updateClasse(id, dto);
        return updated != null ? new ResponseEntity<>(updated, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return classeService.deleteClasse(id) ? 
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : 
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/enseignants")
    public ResponseEntity<List<EnseignantDTO>> getEnseignantsByClasse(@PathVariable Long id) {
        List<EnseignantDTO> enseignants = classeService.getEnseignantsByClasseId(id);
        return new ResponseEntity<>(enseignants, HttpStatus.OK);
    }
}