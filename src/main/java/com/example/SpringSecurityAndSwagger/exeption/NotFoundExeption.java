package com.example.SpringSecurityAndSwagger.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExeption extends RuntimeException {
    public NotFoundExeption(String msg) {
        super(msg);
    }
}
