package com.katafoni.filemanager.security.user;

public interface UserService {
    UserDto register(UserDto userDto);
    User getCurrentUser();
}
