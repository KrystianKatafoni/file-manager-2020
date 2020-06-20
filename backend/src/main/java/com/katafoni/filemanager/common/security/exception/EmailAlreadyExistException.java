package com.katafoni.filemanager.common.security.exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(String email) {
        super("User with email " + email +" already exist");
    }
}
