package com.katafoni.filemanager.file.exception;

public class FileHasIncorrectFormatException extends RuntimeException{
    public FileHasIncorrectFormatException(String fileName) {
        super("Document has incorrect format, file name:  " + fileName);
    }
}
