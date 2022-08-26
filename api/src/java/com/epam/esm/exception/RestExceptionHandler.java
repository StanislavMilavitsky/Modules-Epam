package com.epam.esm.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.*;

/**
 * Class for catching exceptions
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private final MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();
    private final AtomicInteger atomicInteger = new AtomicInteger();

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * If api throw service exception we create our custom exception and code auto increment for all users
     *
     * @param exception from service
     * @return our custom exception
     */
    @ExceptionHandler(ServiceException.class)
    private ResponseEntity<ErrorResponse> handlerServiceException(ServiceException exception) {
        String message = messageSource.getMessage("service", null, locale);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponseHandler(BAD_REQUEST, message +
                " " + exception.getLocalizedMessage()));
    }

    /**
     * If we had Controller we catch then return our exception
     *
     * @param exception runtime from api that illegal validation
     * @return custom exception
     */

    @ExceptionHandler(ControllerException.class)
    private ResponseEntity<ErrorResponse> handlerControllerException(ControllerException exception) {
        String message = messageSource.getMessage("controller", null, locale);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponseHandler(BAD_REQUEST, message +
                " " + exception.getLocalizedMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        String internationale = messageSource.getMessage("runtime", null, locale);
        String message = exception.getClass().getName() + " : " + exception.getMessage();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponseHandler(INTERNAL_SERVER_ERROR,
                internationale + " " + message));
    }

    /**
     * If we had IllegalArgumentException or IllegalStateException we catch then return our exception
     *
     * @param exception runtime from api if input parameters bad
     * @return custom exception
     */
    @ExceptionHandler(JsonParseException.class)
    private ResponseEntity<ErrorResponse> handlerInputException(RuntimeException exception) {
        String internationale = messageSource.getMessage("jsonParse", null, locale);
        String message = exception.getClass().getName() + " : " + exception.getMessage();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponseHandler(INTERNAL_SERVER_ERROR,
                internationale + " " + message));
    }

    @ExceptionHandler(NotExistEntityException.class)
    private ResponseEntity<ErrorResponse> handleNotExistException(NotExistEntityException exception) {
        String message = messageSource.getMessage("notExistEntity", null, locale);
        return ResponseEntity.status(NOT_FOUND).body(errorResponseHandler(NOT_FOUND,
                message + " " + exception.getLocalizedMessage()));
    }

    @ExceptionHandler(IncorrectArgumentException.class)
    private ResponseEntity<ErrorResponse> handleIncorrectArgumentException(IncorrectArgumentException exception) {
        String internationale = messageSource.getMessage("incorrectArgument", null, locale);
        String message = exception.getClass().getName() + " : " + exception.getMessage();
        return ResponseEntity.status(BAD_REQUEST).body(errorResponseHandler(BAD_REQUEST,
                internationale + " " + message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        StringBuilder builder = new StringBuilder();
        exception.getBindingResult().getAllErrors().forEach(e -> {
            String fieldName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();
            builder.append(fieldName).append(" ").append(errorMessage).append("; ");
        });
        return ResponseEntity.status(BAD_REQUEST).body(errorResponseHandler(BAD_REQUEST, builder.toString()));
    }

    /**
     * @param status    is
     * @return Custom entity exception
     */
    private ErrorResponse errorResponseHandler(HttpStatus status, String message) {
        return new ErrorResponse(message, status.value() * 100 + atomicInteger.incrementAndGet());
    }

}
