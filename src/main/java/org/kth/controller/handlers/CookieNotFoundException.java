package org.kth.controller.handlers;

/**
 * Created by Sebastian on 2016-11-26.
 */
public class CookieNotFoundException extends Exception {
    public CookieNotFoundException() {
    }

    public CookieNotFoundException(String message) {
        super(message);
    }

    public CookieNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieNotFoundException(Throwable cause) {
        super(cause);
    }

    public CookieNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
