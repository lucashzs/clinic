package com.api.clinic.services;

import com.api.clinic.dtos.PatientCreateDto;
import com.api.clinic.dtos.PatientDto;
import com.api.clinic.entities.Patient;
import com.api.clinic.repositories.PatientRepository;
import com.api.clinic.services.servicesExceptions.NotFoundException;
import com.api.clinic.services.servicesExceptions.RelatedEntitiesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient findByDocument(String document) {
        Optional<Patient> patient = patientRepository.findById(document);
        return patient.orElseThrow(() -> new NotFoundException(
                "Document Not Found! ID: " + document
        ));
    }

    @Transactional
    public ResponseEntity<Object> create(PatientCreateDto patientCreateDto) {
        Patient patient = new Patient(patientCreateDto);
        this.patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient created successfully!");
    }

    @Transactional
    public ResponseEntity<Object> update(PatientDto patientDto, String document) {
        Patient patient = findByDocument(document);
        patient.setName(patientDto.name());
        patient.setDateTime(patientDto.dateTime());
        this.patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Update Successfully %s", patientDto));
    }

    public ResponseEntity<Object> delete(String document) {
        findByDocument(document);
        try {
            this.patientRepository.deleteById(document);
        } catch (Exception e) {
            throw new RelatedEntitiesExceptions("Cannot delete as we cannot find this document!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Delete Successfully %s", document));
    }
}
