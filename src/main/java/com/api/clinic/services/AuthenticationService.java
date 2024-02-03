package com.api.clinic.services;

import com.api.clinic.dtos.LoginDoctorDto;
import com.api.clinic.dtos.LoginResponseDto;
import com.api.clinic.dtos.RegisterDoctorDto;
import com.api.clinic.entities.Doctor;
import com.api.clinic.repositorys.DoctorRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private DoctorRepository doctorRepository;
    private TokenService tokenService;

    public AuthenticationService(DoctorRepository doctorRepository, TokenService tokenService) {
        this.doctorRepository = doctorRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    public ResponseEntity<Object> login(LoginDoctorDto loginData) {

        var doctor = this.doctorRepository.findByEmail(loginData.email());

        if (doctor == null || !new BCryptPasswordEncoder().matches(loginData.password(), doctor.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        var token = tokenService.generationToken(doctor);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Transactional
    @Modifying
    public ResponseEntity<Object> register(RegisterDoctorDto registerData) {
        Doctor newDoctor;
        if (registerData.password().equals(registerData.confirmPassword())) {
            if (this.doctorRepository.findByEmail(registerData.email()) != null)
                return ResponseEntity.badRequest().build();
            newDoctor = new Doctor(registerData, EncryptPasswordService.encryptPassword(registerData.password()));
        } else {
            return ResponseEntity.badRequest().body("The passwords are not the same");
        }

        this.doctorRepository.save(newDoctor);
        return ResponseEntity.ok().build();

    }
}
