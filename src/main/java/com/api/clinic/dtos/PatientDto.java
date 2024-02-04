package com.api.clinic.dtos;

import java.time.LocalDateTime;

public record PatientDto(String name, LocalDateTime dateTime) {
}
