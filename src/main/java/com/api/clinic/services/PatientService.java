package com.api.clinic.services;

import com.api.clinic.dtos.PatientCreateDto;
import com.api.clinic.dtos.PatientDto;
import com.api.clinic.entities.Patient;
import com.api.clinic.repositories.PatientRepository;
import com.api.clinic.services.servicesExceptions.RelatedEntitiesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient findByDocument(String document) {
        return this.patientRepository.findById(document).orElseThrow(() -> new RelatedEntitiesExceptions("Document Not Found! ID: " + document));
    }

    private boolean verifyDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            LocalDateTime.parse(dateTime, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Transactional
    public ResponseEntity<Object> create(PatientCreateDto patientCreateDto) {
        if (patientCreateDto.name() == null || patientCreateDto.name().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid name!");
        }
        if (!verifyDateTime(patientCreateDto.dateTime())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid dateTime, put it in the format: 'yyyy-mm-ddThh:mm:ss'");
        }
        LocalDateTime dateTime = LocalDateTime.parse(patientCreateDto.dateTime());
        if (this.patientRepository.existsByDocument(patientCreateDto.document())) {
            throw new RelatedEntitiesExceptions("Document already exists!");
        }
        Patient patient = new Patient(patientCreateDto);
        this.patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient created successfully!");
    }

    @Transactional
    public ResponseEntity<Object> update(PatientDto patientDto, String document) {
        if (!verifyDateTime(patientDto.dateTime())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid dateTime, put it in the format: 'yyyy-mm-ddThh:mm:ss'");
        }
        Patient patient = findByDocument(document);
        patient.setName(patientDto.name());
        patient.setDateTime(String.valueOf(patientDto.dateTime()));
        this.patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.OK).body("Update Successfully!");
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
