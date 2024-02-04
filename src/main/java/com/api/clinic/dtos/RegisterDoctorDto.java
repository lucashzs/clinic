package com.api.clinic.dtos;

public record RegisterDoctorDto (String document, String email, String password, String confirmPassword, String specialty, String birthDate, String telephone){

}
