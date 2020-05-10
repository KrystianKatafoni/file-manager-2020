package com.katafoni.filemanager.security.validator;



import com.katafoni.filemanager.security.user.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatcher implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        UserDto userDto = (UserDto) obj;
        return userDto.getPassword().equals(userDto.getMatchingPassword());
    }

    @Override
    public void initialize(PasswordMatches constraintAnnotation) { }
}
