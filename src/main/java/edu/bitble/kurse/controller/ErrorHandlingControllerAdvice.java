package edu.bitble.kurse.controller;

import edu.bitble.kurse.common.ControllerUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static edu.bitble.kurse.common.ControllerUtils.buildErrorResponse;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String, Object> onConstraintValidationException(ConstraintViolationException e) {
        var fields = new LinkedHashMap<String, String>();
        e.getConstraintViolations().forEach(
                violation -> fields.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        return ControllerUtils.buildErrorResponse(e, fields);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Object onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fields = new LinkedHashMap<String, String>();
        e.getBindingResult().getFieldErrors().forEach(
                violation -> fields.put(violation.getField(), violation.getDefaultMessage())
        );
        return ControllerUtils.buildErrorResponse(e, fields);
    }

    @ExceptionHandler({NoSuchElementException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    Map<String, Object> onNotFoundException(Exception e) {
        return ControllerUtils.buildErrorResponse(e);
    }
    
    @ExceptionHandler({IllegalArgumentException.class, NoSuchFieldException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String, Object> onIllegalArgumentAndNoSuchFieldException(Exception e) {
        var parts = e.getMessage().split(":");
        return ControllerUtils.buildErrorResponse(e, Map.of(parts[0], parts[1]));
    }
    
    @ExceptionHandler({UnsupportedOperationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<?> onUnsupportedOperationException(Exception e) {
        return ResponseEntity.internalServerError().body(
                Map.of("description", e.getMessage())
        );
    }
    
}
