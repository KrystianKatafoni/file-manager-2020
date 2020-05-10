package com.katafoni.filemanager.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserMapperDecorator implements UserMapper  {
    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Override
    public User userDtoToUser(UserDto userDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = delegate.userDtoToUser(userDto);
        user.setUsername(user.getUsername().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }
}
