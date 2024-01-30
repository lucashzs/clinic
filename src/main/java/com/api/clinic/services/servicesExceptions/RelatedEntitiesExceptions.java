package com.api.clinic.services.servicesExceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RelatedEntitiesExceptions extends DataIntegrityViolationException {

    public RelatedEntitiesExceptions (String message){
        super(message);
    }
}
