package com.example.userservice.service;

import com.example.userservice.dto.UserDto;

public interface UserService {
    Long createUser(UserDto userDto);
}
