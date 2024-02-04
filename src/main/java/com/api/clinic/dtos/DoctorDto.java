package com.api.clinic.dtos;

import com.api.clinic.entities.Doctor;

public record DoctorDto(String email,String password, String specialty, String birthDate, String telephone) {

    public DoctorDto(Doctor doctor) {
        this(doctor.getEmail(),doctor.getPassword(), doctor.getSpecialty(), doctor.getBirthDate(), doctor.getTelephone());
    }
}
