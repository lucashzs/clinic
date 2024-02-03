package com.api.clinic.dtos;

import java.time.LocalDate;

public record DoctorDto(String email, String password, String specialty, LocalDate birthDate, String telephone) {
}
