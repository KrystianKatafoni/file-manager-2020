package com.katafoni.filemanager.security.exception;

public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException(String username) {
        super("User " + username + " not registered. Please try one more time.");
    }
}
