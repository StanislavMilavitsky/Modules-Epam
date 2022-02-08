package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class RestExceptionHandler {

    private final AtomicInteger atomicInteger = new AtomicInteger();

    @ExceptionHandler(ServiceException.class)
    private ResponseEntity<ErrorResponse> handleException(ServiceException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse =
                new ErrorResponse(exception.getLocalizedMessage(),
                        status.value() * 100 + atomicInteger.getAndIncrement());//todo разобраться
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    private ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getLocalizedMessage());
    }
}
