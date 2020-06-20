package com.katafoni.filemanager.common.security.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException() {
        super("Credentials for user are invalid");
    }
}
