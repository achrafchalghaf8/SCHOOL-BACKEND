package com.cscorner.controllers;

import com.cscorner.dto.ParentDTO;
import com.cscorner.services.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentService parentService;

    @PostMapping
    public ResponseEntity<ParentDTO> createParent(@RequestBody ParentDTO dto) {
        try {
            return new ResponseEntity<>(parentService.createParent(dto), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ParentDTO>> getAllParents() {
        return new ResponseEntity<>(parentService.getAllParents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentDTO> getParentById(@PathVariable Long id) {
        ParentDTO parent = parentService.getParentById(id);
        return parent != null ? 
                new ResponseEntity<>(parent, HttpStatus.OK) : 
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentDTO> updateParent(@PathVariable Long id, @RequestBody ParentDTO dto) {
        try {
            ParentDTO updatedParent = parentService.updateParent(id, dto);
            return updatedParent != null ? 
                    new ResponseEntity<>(updatedParent, HttpStatus.OK) : 
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        boolean deleted = parentService.deleteParent(id);
        return deleted ? 
                new ResponseEntity<>(HttpStatus.NO_CONTENT) : 
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/verify-sync")
    public ResponseEntity<String> verifyParentSync(@PathVariable Long id) {
        String result = parentService.verifyParentSync(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}