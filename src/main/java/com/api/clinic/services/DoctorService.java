//package com.api.clinic.services;
//
//import com.api.clinic.entities.Doctor;
//import com.api.clinic.repositorys.DoctorRepository;
//import com.api.clinic.services.servicesExceptions.NotFoundException;
//import com.api.clinic.services.servicesExceptions.RelatedEntitiesExceptions;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//public class DoctorService {
//
//    private final DoctorRepository doctorRepository;
//
//    public DoctorService (DoctorRepository doctorRepository){
//        this.doctorRepository = doctorRepository;
//    }
//
//    public Doctor findByDocument(String document){
//        Optional<Doctor> doctor = this.doctorRepository.findById(document);
//        return doctor.orElseThrow(() -> new NotFoundException(
//                    "Document Not Found! ID: " + document
//                ));
//    }
//
//    @Transactional
//    public Doctor create (Doctor doc){
//        doc = this.doctorRepository.save(doc);
//        return doc;
//    }
//
//    @Transactional
//    public Doctor update (Doctor doc){
//        Doctor newDoc = findByDocument(doc.getDocument());
//        newDoc.setPassword(doc.getPassword());
//        this.doctorRepository.save(newDoc);
//        return newDoc;
//    }
//
//    public void delete (String document){
//        findByDocument(document);
//        try {
//            this.doctorRepository.deleteById(document);
//        } catch (Exception e){
//            throw new RelatedEntitiesExceptions("Cannot delete as we cannot find this document!");
//        }
//    }
//}
