package com.katafoni.filemanager.file.exception;

import com.katafoni.filemanager.common.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class FileAdvice {

    private static final String MESSAGE_CANNOT_STORE_FILE = "Cannot store file. Please try later.";
    private static final String MESSAGE_FILE_DOESNT_EXIST = "Requested file doesn't exist.";
    private static final String MESSAGE_FILE_HAS_INCORRECT_FORMAT = "File has incorrect format";
    private static final String MESSAGE_STORING = "Unexpected problem with storing a file";
    private static final String MESSAGE_BYTES = "Unexpected problem with file conversion";


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CannotStoreFileException.class)
    public ResponseEntity<Object> cannotStoreFileHandler(CannotStoreFileException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE_CANNOT_STORE_FILE, ex));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileDoesntExistException.class)
    public ResponseEntity<Object> fileDoesntExistHandler(FileDoesntExistException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, MESSAGE_FILE_DOESNT_EXIST, ex));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileHasIncorrectFormatException.class)
    public ResponseEntity<Object> fileHasInncorectFormatHandler(FileHasIncorrectFormatException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, MESSAGE_FILE_HAS_INCORRECT_FORMAT, ex));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileStoringException.class)
    public ResponseEntity<Object> fileStoringHandler(FileStoringException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE_STORING, ex));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileToBytesException.class)
    public ResponseEntity<Object> fileToBytesHandler(FileToBytesException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE_BYTES, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}