package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;

public interface UserService {

    Long createUser(UserDto userDto);

    UserDto getUserByUsername(String username);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
