package com.katafoni.filemanager.security.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException() {
        super("Credentials for user are invalid");
    }
}
