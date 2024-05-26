package edu.bitble.kurse.controller;

import edu.bitble.kurse.common.ControllerUtils;
import edu.bitble.kurse.common.exception.AuthorizationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    Map<String, Object> onAuthenticationException(Exception e) {
        return ControllerUtils.buildErrorResponse(e);
    }

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
        var fields = new LinkedHashMap<String, List<String>>();
        e.getBindingResult().getFieldErrors().forEach(violation -> {
            var initializedInvalidField = fields.putIfAbsent(violation.getField(), new ArrayList<>());
            if (initializedInvalidField == null) {
                var errorMessage = violation.getDefaultMessage();
                if (errorMessage != null) {
                    errorMessage = errorMessage.replace(violation.getField() + ":", "");
                }

                fields.get(violation.getField()).add(errorMessage);
            }
        });
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
