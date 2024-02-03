package com.api.clinic.entities;

import com.api.clinic.dtos.RegisterDoctorDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Doctor implements UserDetails {


    @NotNull
    @Column(name = "cpf")
    @Id
    @CPF
    private String document;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 8)
    private String password;

    @NotBlank
    @NotNull
    private String specialty;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String telephone;

    public Doctor(String email, String password, String document) {
        this.email = email;
        this.password = password;
        this.document = document;
    }

    public Doctor(RegisterDoctorDto data, String encryptPassword) {
        this.document = data.document();
        this.email = data.email();
        this.password = encryptPassword;
        this.specialty = data.specialty();
        this.birthDate = data.birthDate();
        this.telephone = data.telephone();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return document;
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
