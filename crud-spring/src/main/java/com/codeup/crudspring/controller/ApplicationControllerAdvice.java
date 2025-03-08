package com.codeup.crudspring.controller;

import com.codeup.crudspring.dto.MessageErrorDTO;
import com.codeup.crudspring.exception.RecordNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageErrorDTO handleNotFoundException(RecordNotFoundException ex) {
        return new MessageErrorDTO(LocalDateTime.now(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public MessageErrorDTO handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new MessageErrorDTO(LocalDateTime.now(), ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage()).toList());

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public MessageErrorDTO handlerMethodArgumentNotValidException(ConstraintViolationException ex) {
        return new MessageErrorDTO(LocalDateTime.now(), ex.getConstraintViolations().stream()
                .map(error -> error.getPropertyPath() + " " + error.getMessage()).toList());

    }

}
