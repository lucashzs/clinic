package com.api.clinic.services;

import com.api.clinic.dtos.DoctorDto;
import com.api.clinic.entities.Doctor;
import com.api.clinic.repositorys.DoctorRepository;
import com.api.clinic.services.servicesExceptions.NotFoundException;
import com.api.clinic.services.servicesExceptions.RelatedEntitiesExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.print.Doc;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    Doctor doctor;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor findByDocument(String document) {
        Optional<Doctor> doctor = this.doctorRepository.findById(document);
        return doctor.orElseThrow(() -> new NotFoundException(
                "Document Not Found! ID: " + document
        ));
    }

    @Transactional
    public Doctor create(Doctor doc) {
        doc = this.doctorRepository.save(doc);
        return doc;
    }

    public Doctor searchUser(@PathVariable String document){
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        if (!doctorRepository.existsByDocument(document) || authenticated instanceof AnonymousAuthenticationToken){
            throw new RelatedEntitiesExceptions("You can not update in other user");
        } return doctor;
    }

    public void canDeleteDoctor(String document){
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if(authenticated instanceof AnonymousAuthenticationToken){
            throw new AccessDeniedException("You are not authorized to delete a doctor");
        } else {
            userDetails = (UserDetails) authenticated.getPrincipal();
            Long authenticatedDoctorId = Long.valueOf(userDetails.getUsername());
            if (!document.equals(String.valueOf(authenticatedDoctorId)))
                throw new AccessDeniedException("A doctor can only delete himself");
        }
    }

    @Transactional
    public void update(DoctorDto doctorDto,String document) {
        searchUser(document);
        Doctor newDoctor = findByDocument(document);
        newDoctor.setPassword(EncryptPasswordService.encryptPassword(doctorDto.password()));
        newDoctor.setTelephone(doctorDto.telephone());
        newDoctor.setEmail(doctorDto.email());
        newDoctor.setSpecialty(doctorDto.specialty());
        newDoctor.setBirthDate(doctorDto.birthDate());
        this.doctorRepository.save(newDoctor);
    }

    public void delete(String document) {
        canDeleteDoctor(document);
        try {
            this.doctorRepository.deleteById(document);
        } catch (Exception e) {
            throw new RelatedEntitiesExceptions("Cannot delete as we cannot find this document!");
        }
    }
}
