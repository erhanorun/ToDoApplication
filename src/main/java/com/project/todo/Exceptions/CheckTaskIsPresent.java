package com.project.todo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CheckTaskIsPresent extends RuntimeException {
    public CheckTaskIsPresent(String message) {
        super(message);
    }
}
