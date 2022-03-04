package com.epam.esm.exception;

public class NotExistEntityException extends Exception {

    public NotExistEntityException() {
        super();
    }

    public NotExistEntityException(String message) {
        super(message);
    }

    public NotExistEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistEntityException(Throwable cause) {
        super(cause);
    }
}
}
