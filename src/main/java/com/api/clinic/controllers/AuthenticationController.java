package com.api.clinic.controllers;

import com.api.clinic.dtos.LoginDoctorDto;
import com.api.clinic.dtos.RegisterDoctorDto;
import com.api.clinic.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDoctorDto data) {
        return this.authenticationService.login(data);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDoctorDto data) {
        return this.authenticationService.register(data);
    }
}