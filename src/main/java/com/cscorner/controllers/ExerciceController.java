package com.cscorner.controllers;

import com.cscorner.dto.ExerciceDTO;
import com.cscorner.services.ExerciceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercices")
public class ExerciceController {

    @Autowired
    private ExerciceService exerciceService;

    @PostMapping
    public ResponseEntity<ExerciceDTO> create(@RequestBody ExerciceDTO dto) {
        return new ResponseEntity<>(exerciceService.createExercice(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExerciceDTO>> getAll() {
        return new ResponseEntity<>(exerciceService.getAllExercices(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciceDTO> getById(@PathVariable Long id) {
        ExerciceDTO dto = exerciceService.getExerciceById(id);
        return dto != null ?
                new ResponseEntity<>(dto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciceDTO> update(@PathVariable Long id, @RequestBody ExerciceDTO dto) {
        ExerciceDTO updated = exerciceService.updateExercice(id, dto);
        return updated != null ?
                new ResponseEntity<>(updated, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return exerciceService.deleteExercice(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
