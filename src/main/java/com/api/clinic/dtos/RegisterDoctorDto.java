package com.api.clinic.dtos;

import java.time.LocalDate;

public record RegisterDoctorDto (String document, String email, String password, String confirmPassword, String specialty, LocalDate birthDate, String telephone){

}
