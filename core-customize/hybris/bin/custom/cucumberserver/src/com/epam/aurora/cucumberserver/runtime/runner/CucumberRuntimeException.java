package com.epam.aurora.cucumberserver.runtime.runner;

public class CucumberRuntimeException extends RuntimeException {
    public CucumberRuntimeException(Throwable cause) {
        super(cause);
    }

    public CucumberRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
