package com.api.clinic.dtos;

import com.api.clinic.entities.Patient;

import java.time.LocalDateTime;

public record PatientDto(String name, LocalDateTime dateTime) {
    public PatientDto(Patient patient) {
        this(patient.getName(), patient.getDateTime());
    }
}
