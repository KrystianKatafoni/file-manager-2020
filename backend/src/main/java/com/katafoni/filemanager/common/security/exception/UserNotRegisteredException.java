package com.katafoni.filemanager.common.security.exception;

public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException(String username) {
        super("User " + username + " not registered. Please try one more time.");
    }
}
