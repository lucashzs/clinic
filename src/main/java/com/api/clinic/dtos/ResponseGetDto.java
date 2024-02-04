package com.api.clinic.dtos;

import com.api.clinic.entities.Doctor;


public record ResponseGetDto (String email, String specialty, String birthDate, String telephone) {

    public ResponseGetDto(Doctor doctor) {
        this(doctor.getEmail(),doctor.getSpecialty(), doctor.getBirthDate(), doctor.getTelephone());
    }
}