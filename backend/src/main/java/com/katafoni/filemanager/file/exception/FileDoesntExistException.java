package com.katafoni.filemanager.file.exception;

public class FileDoesntExistException extends RuntimeException{
    public FileDoesntExistException(String message) {
        super(message);
    }
}
