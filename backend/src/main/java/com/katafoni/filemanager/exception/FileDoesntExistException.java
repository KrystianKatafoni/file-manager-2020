package com.katafoni.filemanager.exception;

public class FileDoesntExistException extends RuntimeException{
    public FileDoesntExistException(String message) {
        super(message);
    }
}
