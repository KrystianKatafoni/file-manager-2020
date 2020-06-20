package com.katafoni.filemanager.extension.exception;

import com.katafoni.filemanager.common.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExtensionAdvice {
    private static final String MESSAGE_EXTENSION_EXIST = "Extension already exist";
    private static final String MESSAGE_INCORRECT_EXTENSION = "File has incorrect extension";

    @ExceptionHandler(ExtensionAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> extensionExistHandler(ExtensionAlreadyExistException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, MESSAGE_EXTENSION_EXIST, ex));
    }

    @ExceptionHandler(FileHasIncorrectExtensionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> fileIncorrectExtensionHandler(FileHasIncorrectExtensionException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, MESSAGE_INCORRECT_EXTENSION, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
