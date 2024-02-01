package com.api.clinic.controllers;

import com.api.clinic.entities.Patient;
import com.api.clinic.services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/attendance")
public class PatientController {

    private final PatientService patientService;

    public PatientController (PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("/{document}")
    public ResponseEntity<Patient> findByDocument (@PathVariable String document){
        Patient patient = this.patientService.findByDocument(document);
        return  ResponseEntity.ok().body(patient);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Patient user){
        this.patientService.create(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{document}").buildAndExpand(user.getDocument()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{document}")
    public ResponseEntity<Void> update (@RequestBody Patient pat, @PathVariable String document){
        pat.setDocument(document);
        this.patientService.update(pat);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable String document){
        this.patientService.delete(document);
        return ResponseEntity.noContent().build();
    }
}
