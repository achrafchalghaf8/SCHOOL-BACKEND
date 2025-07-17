package com.cscorner.controllers;

import com.cscorner.dto.AdminDTO;
import com.cscorner.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminDTO> create(@RequestBody AdminDTO dto) {
        return new ResponseEntity<>(adminService.createAdmin(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAll() {
        return new ResponseEntity<>(adminService.getAllAdmins(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getById(@PathVariable Long id) {
        AdminDTO dto = adminService.getAdminById(id);
        return dto != null ?
                new ResponseEntity<>(dto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> update(@PathVariable Long id, @RequestBody AdminDTO dto) {
        AdminDTO updated = adminService.updateAdmin(id, dto);
        return updated != null ?
                new ResponseEntity<>(updated, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return adminService.deleteAdmin(id) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/verify-sync")
    public ResponseEntity<String> verifySync(@PathVariable Long id) {
        boolean isSync = adminService.verifyAdminCompteSync(id);
        String message = isSync ?
            "Admin et Compte sont synchronisés avec l'ID: " + id :
            "Désynchronisation détectée pour l'ID: " + id;
        return new ResponseEntity<>(message, isSync ? HttpStatus.OK : HttpStatus.CONFLICT);
    }
}
