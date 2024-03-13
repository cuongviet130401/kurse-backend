package edu.uit.kurse.kursebackend.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String, Object> onConstraintValidationException(ConstraintViolationException e) {
        return buildErrorResponse(e, e.getConstraintViolations().stream()
                .map(violation -> Map.of(
                        "field", violation.getPropertyPath().toString(),
                        "invalidValue", String.valueOf(violation.getInvalidValue()),
                        "message", violation.getMessage()
                )).toList()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Object onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return buildErrorResponse(e, e.getDetailMessageArguments());
    }

    @ExceptionHandler({NoSuchElementException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    Map<String, Object> onNotFoundException(Exception e) {
        return buildErrorResponse(e);
    }
    
    @ExceptionHandler({IllegalArgumentException.class, NoSuchFieldException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String, Object> onIllegalArgumentAndNoSuchFieldException(Exception e) {
        return buildErrorResponse(e);
    }
    
    @ExceptionHandler({UnsupportedOperationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<?> onUnsupportedOperationException(Exception e) {
        return ResponseEntity.internalServerError().body(
                Map.of("description", e.getMessage())
        );
    }

    private Map<String, Object> buildErrorResponse(Exception e, Object description) {
        return Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "exceptionClass", e.getClass().getName(),
                "description", description
        );
    }

    private Map<String, Object> buildErrorResponse(Exception e) {
        return buildErrorResponse(e, e.getMessage());
    }
    
}
