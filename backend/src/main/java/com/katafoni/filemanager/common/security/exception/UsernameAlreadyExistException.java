package com.katafoni.filemanager.common.security.exception;

public class UsernameAlreadyExistException extends RuntimeException{
    public UsernameAlreadyExistException(String username) {
        super("User with username " + username + " already exist");
    }
}
