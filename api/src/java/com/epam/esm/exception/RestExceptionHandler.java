package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for catching exceptions
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private final AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * If api throw service exception we create our custom exception and code auto increment for all users
     * @param exception from service
     * @return our custom exception
     */
    @ExceptionHandler(ServiceException.class)
    private ResponseEntity<ErrorResponse> handleException(ServiceException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse =
                new ErrorResponse(exception.getLocalizedMessage(),
                        status.value() * 10 + atomicInteger.getAndIncrement());
        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * If we had IllegalArgumentException or IllegalStateException we catch then return our exception
     * @param exception runtime from api
     * @return custom exception
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    private ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getLocalizedMessage());
    }
}
