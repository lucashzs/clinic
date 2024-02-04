package com.api.clinic.dtos;

import com.api.clinic.entities.Patient;


public record PatientDto(String name, String dateTime) {
    public PatientDto(Patient patient) {
        this(patient.getName(), String.valueOf(patient.getDateTime()));
    }
}
