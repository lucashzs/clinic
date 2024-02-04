package com.api.clinic.entities;

import com.api.clinic.dtos.PatientCreateDto;
import com.api.clinic.dtos.PatientDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patient {

    @NotBlank
    @NotNull
    private String name;

    @Id
    @NotBlank
    @NotNull
    private String document;

    @NotNull
    private String dateTime;

    public Patient (PatientDto patientDto){
        this.name = patientDto.name();
        this.dateTime = String.valueOf(patientDto.dateTime());
    }

    public Patient (PatientCreateDto patientCreateDto){
        this.document = patientCreateDto.document();
        this.name = patientCreateDto.name();
        this.dateTime = patientCreateDto.dateTime();
    }

}
