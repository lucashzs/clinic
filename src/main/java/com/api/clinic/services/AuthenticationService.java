package com.api.clinic.services;

import com.api.clinic.dtos.LoginDoctorDto;
import com.api.clinic.dtos.LoginResponseDto;
import com.api.clinic.dtos.RegisterDoctorDto;
import com.api.clinic.entities.Doctor;
import com.api.clinic.repositories.DoctorRepository;
import com.api.clinic.services.servicesExceptions.RelatedEntitiesExceptions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class AuthenticationService {

    private final DoctorRepository doctorRepository;
    private final TokenService tokenService;

    public AuthenticationService(DoctorRepository doctorRepository, TokenService tokenService) {
        this.doctorRepository = doctorRepository;
        this.tokenService = tokenService;
    }
    private boolean verifyDate(String date) {
        DateTimeFormatter formatter =  DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Transactional
    public ResponseEntity<Object> login(LoginDoctorDto loginData) {
        if (loginData.password() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid login data!");
        }
        var doctor = this.doctorRepository.findByEmail(loginData.email()).orElseThrow(() -> new RelatedEntitiesExceptions("The data's entered is invalid!"));
        if (!new BCryptPasswordEncoder().matches(loginData.password(), doctor.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!");
        }
        var token = tokenService.generationToken(doctor);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Transactional
    @Modifying
    public ResponseEntity<Object> register(RegisterDoctorDto registerData) {
        Doctor newDoctor;
        if (registerData.password().equals(registerData.confirmPassword())) {
            var parts = registerData.email().split("@");
            if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address sent in an invalid format!");
            }
            if(!verifyDate(String.valueOf(registerData.birthDate()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid birthDate, put it in the format: yyyy-mm-dd");
            }
            LocalDate date = LocalDate.parse(registerData.birthDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            if (this.doctorRepository.findByEmail(registerData.email()).isPresent())
                return ResponseEntity.badRequest().build();
            newDoctor = new Doctor(registerData, EncryptPasswordService.encryptPassword(registerData.password()));
        } else {
            return ResponseEntity.badRequest().body("The passwords are not the same!");
        }
        this.doctorRepository.save(newDoctor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Successfully!");
    }
}
