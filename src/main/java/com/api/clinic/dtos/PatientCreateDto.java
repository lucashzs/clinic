package com.api.clinic.dtos;

import java.time.LocalDateTime;

public record PatientCreateDto(String name, LocalDateTime dateTime, String document){
}
