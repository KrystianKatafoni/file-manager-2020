package com.katafoni.filemanager.common.security.exception;


import com.katafoni.filemanager.common.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class SecurityAdvice {

    private static final String MESSAGE_USER_EXIST = "Username exist. Choose another username.";
    private static final String MESSAGE_EMAIL_EXIST = "Username with email already registered. Choose another email.";
    private static final String MESSAGE_USER_NOT_REGISTERED = "User not registered. Internal error has occured. Please try one more time";
    private static final String MESSAGE_TOKEN_INVALID = "Token is invalid. Please send valid token";
    private static final String MESSAGE_USER_CREDENTIALS_INVALID = "Please use valid credentials";
    private static final String MESSAGE_USER_REG_CODE_INVALID = "Please use valid registration code";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<Object> usernameExistHandler(UsernameAlreadyExistException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, MESSAGE_USER_EXIST, ex));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Object> emailExistHandler(EmailAlreadyExistException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, MESSAGE_EMAIL_EXIST, ex));
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotRegisteredException.class)
    public ResponseEntity<Object> userNotRegisteredHandler(UserNotRegisteredException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, MESSAGE_USER_NOT_REGISTERED, ex));
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> tokenInvalidHandler(InvalidTokenException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, MESSAGE_TOKEN_INVALID, ex));
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> credentialsInvalidHandler(InvalidCredentialsException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, MESSAGE_USER_CREDENTIALS_INVALID, ex));
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidRegistrationCodeException.class)
    public ResponseEntity<Object> registrationCodeInvalidHandler(InvalidRegistrationCodeException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, MESSAGE_USER_REG_CODE_INVALID, ex));
    }
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
