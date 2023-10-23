package org.fpic974.gatewayservice.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CustomException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public CustomException(Throwable cause, String message, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
