package com.RulesTV.RulesTV.handler;

public class MissingAuthenticationTokenException extends RuntimeException {
    public MissingAuthenticationTokenException(String message) {
        super(message);
    }
}