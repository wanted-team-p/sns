package com.wanted.sns.exception;

import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NoResultException.class)
    public ErrorResponse handleNoResultException(final NoResultException e){
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }
  
    @ExceptionHandler(ExternalApiException.class)
    public ErrorResponse handleExternalApiException(final ExternalApiException e){
        return ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
