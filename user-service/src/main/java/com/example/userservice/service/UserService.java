package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Long createUser(UserDto userDto);

    UserDto getUserDetailsByEmail(String username);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
