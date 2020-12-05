package com.project.management.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExist extends RuntimeException {

    private String message;

    public UserAlreadyExist(String message) {
        super(message);
    }
}
