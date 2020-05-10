package com.katafoni.filemanager.security.exception;

public class UsernameAlreadyExistException extends RuntimeException{
    public UsernameAlreadyExistException(String username) {
        super("User with username " + username + " already exist");
    }
}
