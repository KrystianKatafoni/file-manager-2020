package com.katafoni.filemanager.common.security.user;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    UserDto userToUserDto(UserEntity userEntity);
    UserEntity userDtoToUser(UserDto userDto);
}
