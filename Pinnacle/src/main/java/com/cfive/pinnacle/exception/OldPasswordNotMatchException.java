package com.cfive.pinnacle.exception;

public class OldPasswordNotMatchException extends RuntimeException {
    public OldPasswordNotMatchException() {
        super("Old password not match");
    }

    public OldPasswordNotMatchException(String message) {
        super(message);
    }

    public OldPasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OldPasswordNotMatchException(Throwable cause) {
        super(cause);
    }

    public OldPasswordNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
