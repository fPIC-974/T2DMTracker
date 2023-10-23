package org.fpic974.gatewayservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomException extends ResponseStatusException {

    public CustomException(HttpStatus status) {
        super(status);
    }
}
