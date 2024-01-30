package com.api.clinic.dtos;

import com.api.clinic.entities.DoctorRole;

import java.time.LocalDate;

public record RegisterDoctorDto (String document, String email, String password, String confirmPassword, String specialty, LocalDate birthDate, String telephone, DoctorRole role){
}
