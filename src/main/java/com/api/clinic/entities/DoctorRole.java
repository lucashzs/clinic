package com.api.clinic.entities;

import lombok.Getter;

@Getter
public enum DoctorRole {

    ADMIN("admin");

    private final String role;
    DoctorRole (String role){
        this.role = role;

    }
}
