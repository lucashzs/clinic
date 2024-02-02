package com.api.clinic.controllers;

import com.api.clinic.entities.Doctor;
import com.api.clinic.services.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/logged")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{document}")
    public ResponseEntity<Doctor> findByDocument(@PathVariable String document) {
        Doctor doc = this.doctorService.findByDocument(document);
        return ResponseEntity.ok().body(doc);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Doctor user) {
        this.doctorService.create(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{document}").buildAndExpand(user.getDocument()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{document}")
    public ResponseEntity<Void> update(@RequestBody Doctor doc, @PathVariable String document) {
        doc.setDocument(document);
        this.doctorService.update(doc);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String document) {
        this.doctorService.delete(document);
        return ResponseEntity.noContent().build();
    }
}
