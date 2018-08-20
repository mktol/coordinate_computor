package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Snipped found")
public class NoSnippedException extends RuntimeException {
    public NoSnippedException() {
    }

    public NoSnippedException(String message) {
        super(message);
    }
}
