package com.katafoni.filemanager.security.user;


import com.katafoni.filemanager.security.auth.ApplicationUserRole;
import com.katafoni.filemanager.security.exception.EmailAlreadyExistException;
import com.katafoni.filemanager.security.exception.InvalidRegistrationCodeException;
import com.katafoni.filemanager.security.exception.UsernameAlreadyExistException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    UserMapper userMapper;
    UserRepository userRepository;
    @Value("${registrationCode}")
    String registrationCode;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto register(UserDto userDto) {
        if(!userDto.getRegistrationCode().equals(registrationCode))
            throw new InvalidRegistrationCodeException("Invalid registration code");

        userRepository.findByEmail(userDto.getEmail().toLowerCase())
                .ifPresent(s -> {throw new EmailAlreadyExistException(userDto.getEmail());});

        User user = userMapper.userDtoToUser(userDto);
        user.setApplicationUserRole(ApplicationUserRole.STANDARD_USER);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public User getCurrentUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->new UsernameNotFoundException(currentUserEmail));
        return user;
    }
}
