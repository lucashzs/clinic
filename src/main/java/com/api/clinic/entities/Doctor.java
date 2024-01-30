package com.api.clinic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Doctor implements UserDetails {

    @NotNull
    @Column(name = "cpf")
    @Id
    private String document;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 8)
    private String password;

    @NotBlank
    @NotNull
    private String confirmPassword;

    @NotBlank
    @NotNull
    private String specialty;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String telephone;

    @Enumerated(EnumType.STRING)
    private DoctorRole role;

    public Doctor(String email, String password, String document) {
        this.email = email;
        this.password = password;
        this.document = document;
    }
    public Doctor(String email, String encryptPassword, DoctorRole role) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == DoctorRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
