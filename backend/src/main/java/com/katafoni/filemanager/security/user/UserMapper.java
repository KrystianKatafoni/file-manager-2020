package com.katafoni.filemanager.security.user;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
