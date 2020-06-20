package com.katafoni.filemanager.common.security.exception;

public class InvalidRegistrationCodeException extends RuntimeException{
    public InvalidRegistrationCodeException(String message) {
        super(message);
    }
}
