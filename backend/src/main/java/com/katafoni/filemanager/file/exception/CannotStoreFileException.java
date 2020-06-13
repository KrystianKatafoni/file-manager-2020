package com.katafoni.filemanager.file.exception;

public class CannotStoreFileException extends RuntimeException {
    public CannotStoreFileException(String fileName) {
        super("Cannot save file, name: " + fileName);
    }
}
