package com.katafoni.filemanager.common.security.registration;


import com.katafoni.filemanager.common.security.exception.UserNotRegisteredException;
import com.katafoni.filemanager.common.security.user.UserDto;
import com.katafoni.filemanager.common.security.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("registration")
public class RegistrationController {

    UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody UserDto userDto) {
        Optional.ofNullable(userService.register(userDto))
                .orElseThrow(() -> new UserNotRegisteredException(userDto.getEmail()));
    }

}
