package com.katafoni.filemanager.common.security.user;

public interface UserService {
    UserDto register(UserDto userDto);
    UserEntity getCurrentUser();
}
