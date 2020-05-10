package com.katafoni.filemanager.security.exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(String email) {
        super("User with email " + email +" already exist");
    }
}
