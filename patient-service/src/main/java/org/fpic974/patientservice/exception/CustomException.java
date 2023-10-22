package org.fpic974.patientservice.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    public CustomException(String message, HttpStatus status) {
        super(message);
    }
}
