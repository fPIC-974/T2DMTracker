package org.fpic974.noteservice.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final HttpStatus status;

    public CustomException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
