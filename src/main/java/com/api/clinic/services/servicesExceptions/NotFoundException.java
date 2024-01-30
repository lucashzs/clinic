package com.api.clinic.services.servicesExceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class NotFoundException extends EntityNotFoundException {
    public NotFoundException (String message){
        super(message);
    }
}
