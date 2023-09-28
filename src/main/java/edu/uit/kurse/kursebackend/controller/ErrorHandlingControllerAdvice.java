package edu.uit.kurse.kursebackend.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<Map<String, String>> onConstraintValidationException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream().map(violation -> Map.of(
                "field", violation.getPropertyPath().toString(),
                "invalidValue", String.valueOf(violation.getInvalidValue()),
                "message", violation.getMessage()
        )).toList();
    }


}
