package com.api.clinic.controllers;

import com.api.clinic.dtos.DoctorDto;
import com.api.clinic.dtos.ResponseGetDto;
import com.api.clinic.entities.Doctor;
import com.api.clinic.services.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{document}")
    public ResponseEntity<ResponseGetDto> findByDocument(@PathVariable String document) {
        Doctor doctor = this.doctorService.findByDocument(document);
        return ResponseEntity.ok().body(new ResponseGetDto(doctor));
    }

    @PutMapping("/{document}")
    public ResponseEntity<Object> update(@RequestBody DoctorDto doctorDetails, @PathVariable String document) {
        return doctorService.update(doctorDetails, document);
    }

    @DeleteMapping("/{document}")
    public ResponseEntity<Object> deleteByDocument(@PathVariable String document) {
        return doctorService.delete(document);
    }
}
