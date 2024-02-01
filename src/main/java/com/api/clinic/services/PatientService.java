package com.api.clinic.services;

import com.api.clinic.entities.Patient;
import com.api.clinic.repositorys.PatientRepository;
import com.api.clinic.services.servicesExceptions.NotFoundException;
import com.api.clinic.services.servicesExceptions.RelatedEntitiesExceptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService (PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public Patient findByDocument(String document){
        Optional<Patient> patient = patientRepository.findById(document);
        return patient.orElseThrow(() -> new NotFoundException(
                "Document Not Found! ID: " + document
        ));
    }

    @Transactional
    public Patient create (Patient patient){
        patient = this.patientRepository.save(patient);
        return patient;
    }

    @Transactional
    public Patient update(Patient patient){
        Patient newPat = findByDocument(patient.getDocument());
        newPat.setName(patient.getName());
        this.patientRepository.save(newPat);
        return newPat;
    }

    public void delete (String document){
        findByDocument(document);
        try {
            this.patientRepository.deleteById(document);
        } catch (Exception e){
            throw new RelatedEntitiesExceptions("Cannot delete as we cannot find this document!");
        }
    }
}
