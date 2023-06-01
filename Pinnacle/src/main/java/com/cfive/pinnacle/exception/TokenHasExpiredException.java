package com.cfive.pinnacle.exception;

public class TokenHasExpiredException extends RuntimeException {
    public TokenHasExpiredException() {
        super("Token has expired");
    }

    public TokenHasExpiredException(String message) {
        super(message);
    }

    public TokenHasExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenHasExpiredException(Throwable cause) {
        super(cause);
    }

    public TokenHasExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
