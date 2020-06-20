package com.katafoni.filemanager.common.security.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String token) {
        super("Token invalid: " + token);
    }
}
