package com.api.clinic.services;

import com.api.clinic.repositories.DoctorRepository;
import com.api.clinic.services.servicesExceptions.RelatedEntitiesExceptions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final DoctorRepository doctorRepository;

    public AuthorizationService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return doctorRepository.findByEmail(email).orElseThrow(() -> new RelatedEntitiesExceptions("Email Not Found!"));
    }
}
