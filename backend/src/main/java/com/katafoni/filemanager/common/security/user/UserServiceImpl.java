package com.katafoni.filemanager.common.security.user;


import com.katafoni.filemanager.common.security.auth.ApplicationUserRole;
import com.katafoni.filemanager.common.security.exception.EmailAlreadyExistException;
import com.katafoni.filemanager.common.security.exception.InvalidRegistrationCodeException;
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

        UserEntity userEntity = userMapper.userDtoToUser(userDto);
        userEntity.setApplicationUserRole(ApplicationUserRole.STANDARD_USER);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.userToUserDto(savedUserEntity);
    }

    @Override
    public UserEntity getCurrentUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() ->new UsernameNotFoundException(currentUserEmail));
        return userEntity;
    }
}
