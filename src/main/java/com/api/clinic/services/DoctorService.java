package com.api.clinic.services;

import com.api.clinic.dtos.DoctorDto;
import com.api.clinic.entities.Doctor;
import com.api.clinic.repositories.DoctorRepository;
import com.api.clinic.services.servicesExceptions.RelatedEntitiesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor findByDocument(String document) {
        return this.doctorRepository.findById(document).orElseThrow(() -> new RelatedEntitiesExceptions("Document Not Found! ID: " + document));
    }

    @Transactional
    public ResponseEntity<Object> update(DoctorDto doctorDto, String document) {
        searchUser(document);
        Doctor newDoctor = findByDocument(document);
        newDoctor.setPassword(EncryptPasswordService.encryptPassword(doctorDto.password()));
        newDoctor.setTelephone(doctorDto.telephone());
        newDoctor.setEmail(doctorDto.email());
        newDoctor.setSpecialty(doctorDto.specialty());
        newDoctor.setBirthDate(doctorDto.birthDate());
        this.doctorRepository.save(newDoctor);
        return ResponseEntity.status(HttpStatus.OK).body("Update Successfully");
    }

    public ResponseEntity<Object> delete(String document) {
        canDeleteDoctor(document);

        try {
            this.doctorRepository.deleteById(document);
        } catch (Exception e) {
            throw new RelatedEntitiesExceptions("Cannot delete as we cannot find this document!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Delete Successfully DOCUMENT: %s", document));
    }

    public void searchUser(@PathVariable String document) {
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authenticated instanceof AnonymousAuthenticationToken) {
            throw new RelatedEntitiesExceptions("You are not authorized to delete a doctor!");
        } else {
            userDetails = (UserDetails) authenticated.getPrincipal();
            Long authenticatedDoctorId = Long.valueOf(userDetails.getUsername());
            if (!document.equals(String.valueOf(authenticatedDoctorId)))
                throw new RelatedEntitiesExceptions("A doctor can only update himself");
        }
    }

    public void canDeleteDoctor(String document) {
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authenticated instanceof AnonymousAuthenticationToken) {
            throw new RelatedEntitiesExceptions("You are not authorized to delete a doctor!");
        } else {
            userDetails = (UserDetails) authenticated.getPrincipal();
            Long authenticatedDoctorId = Long.valueOf(userDetails.getUsername());
            if (!document.equals(String.valueOf(authenticatedDoctorId)))
                throw new RelatedEntitiesExceptions("A doctor can only delete himself");
        }
    }
}
