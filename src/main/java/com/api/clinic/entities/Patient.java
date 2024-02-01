package com.api.clinic.entities;

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
    private LocalDateTime dateTime;
}
