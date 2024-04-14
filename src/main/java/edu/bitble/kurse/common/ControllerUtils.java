package edu.bitble.kurse.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static edu.bitble.kurse.common.ExceptionLogger.logInvalidAction;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerUtils {

    public static Map<String, Object> buildErrorResponse(Exception e, Object description) {
        return Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "exceptionClass", e.getClass().getName(),
                "description", description
        );
    }

    public static Map<String, Object> buildErrorResponse(Exception e) {
        return buildErrorResponse(e, e.getMessage());
    }

    private static final Map<Class<?>, ?> exceptionalResponseMappings = Map.of(
            NoSuchElementException.class, ResponseEntity.notFound(),
            NullPointerException.class, ResponseEntity.notFound(),
            IllegalArgumentException.class, ResponseEntity.badRequest(),
            NoSuchFieldException.class, ResponseEntity.badRequest(),
            UnsupportedOperationException.class, ResponseEntity.internalServerError()
    );

    public static ResponseEntity<?> controllerWrapper(Supplier<?> controllerExecution) {
        try {
            return ResponseEntity.ok(controllerExecution.get());
        } catch (Exception e) {
            logInvalidAction(e);
            return switchExceptionsResponse(e);
        }
    }

    public static ResponseEntity<?> controllerWrapper(Runnable controllerExecution) {
        try {
            controllerExecution.run();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logInvalidAction(e);
            return switchExceptionsResponse(e);
        }
    }

    private static ResponseEntity<?> switchExceptionsResponse(Exception e) {
        if (exceptionalResponseMappings.containsKey(e.getClass())) {
            return ((ResponseEntity.BodyBuilder) exceptionalResponseMappings.get(e.getClass()))
                    .body(buildErrorResponse(e));
        } else {
            return ResponseEntity.internalServerError().body(ExceptionLogger.ResponseException.fromExceptionObject(e));
        }
    }
}
