package com.api.clinic.controllers;

import com.api.clinic.dtos.PatientCreateDto;
import com.api.clinic.dtos.PatientDto;
import com.api.clinic.entities.Patient;
import com.api.clinic.services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendance")
public class PatientController {

    private final PatientService patientService;

    public PatientController (PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("/{document}")
    public ResponseEntity<PatientDto> findByDocument (@PathVariable String document){
        Patient patient = this.patientService.findByDocument(document);
        return  ResponseEntity.ok().body(new PatientDto(patient));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody PatientCreateDto patientCreateDto){
        return patientService.create(patientCreateDto);
    }

    @PutMapping("/{document}")
    public ResponseEntity<Object> update (@RequestBody PatientDto patientDetails, @PathVariable String document){
        return patientService.update(patientDetails, document);
    }

    @DeleteMapping("/{document}")
    public ResponseEntity<Object> delete (@PathVariable String document){
        return patientService.delete(document);
    }
}
