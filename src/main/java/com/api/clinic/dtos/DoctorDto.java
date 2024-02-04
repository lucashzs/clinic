package com.api.clinic.dtos;

import com.api.clinic.entities.Doctor;

import java.time.LocalDate;

public record DoctorDto(String email, String password, String specialty, LocalDate birthDate, String telephone) {

    public DoctorDto(Doctor doctor) {
        this(doctor.getEmail(), doctor.getPassword(), doctor.getSpecialty(), doctor.getBirthDate(), doctor.getTelephone());
    }
}
