package com.api.clinic.services;

import com.api.clinic.dtos.LoginDoctorDto;
import com.api.clinic.dtos.LoginResponseDto;
import com.api.clinic.dtos.RegisterDoctorDto;
import com.api.clinic.entities.Doctor;
import com.api.clinic.repositorys.DoctorRepository;
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
    public ResponseEntity<Object> login(LoginDoctorDto data) {

        var doctor = this.doctorRepository.findByEmail(data.email());

        if (!new BCryptPasswordEncoder().matches(data.password(), doctor.getPassword())) {
            throw new RuntimeException("Not Found Datas");
        }
        var token = tokenService.generationToken(doctor);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Transactional
    public ResponseEntity<Object> register(RegisterDoctorDto data) {
        if (this.doctorRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptPassword = new BCryptPasswordEncoder().encode(data.password());
        Doctor newDoc = new Doctor(data.email(), encryptPassword, data.role());

        this.doctorRepository.save(newDoc);
        return ResponseEntity.ok().build();

    }
}

