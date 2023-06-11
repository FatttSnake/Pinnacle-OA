package com.cfive.pinnacle.exception;

public class DataValidationFailedException extends RuntimeException {
    public DataValidationFailedException() {
        super("Data validation failed");
    }

    public DataValidationFailedException(String message) {
        super(message);
    }

    public DataValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataValidationFailedException(Throwable cause) {
        super(cause);
    }

    public DataValidationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
