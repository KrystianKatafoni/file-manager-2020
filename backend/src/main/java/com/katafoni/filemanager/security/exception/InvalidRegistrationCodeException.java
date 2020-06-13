package com.katafoni.filemanager.security.exception;

public class InvalidRegistrationCodeException extends RuntimeException{
    public InvalidRegistrationCodeException(String message) {
        super(message);
    }
}
