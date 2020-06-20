package com.katafoni.filemanager.common.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserMapperDecorator implements UserMapper  {
    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Override
    public UserEntity userDtoToUser(UserDto userDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity userEntity = delegate.userDtoToUser(userDto);
        userEntity.setEmail(userEntity.getEmail().toLowerCase());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);
        return userEntity;
    }
}
