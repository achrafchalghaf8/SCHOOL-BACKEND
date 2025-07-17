package com.cscorner.controllers;

import com.cscorner.dto.CourDTO;
import com.cscorner.services.CourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CourController {

    @Autowired
    private CourService courService;

    @PostMapping
    public ResponseEntity<CourDTO> createCour(@RequestBody CourDTO dto) {
        CourDTO created = courService.createCour(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<CourDTO>> getAllCours() {
        return ResponseEntity.ok(courService.getAllCours());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourDTO> getCourById(@PathVariable Long id) {
        CourDTO dto = courService.getCourById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourDTO> updateCour(@PathVariable Long id, @RequestBody CourDTO dto) {
        CourDTO updated = courService.updateCour(id, dto);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCour(@PathVariable Long id) {
        if (courService.deleteCour(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
