package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/")
public class UserController {
    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping(value="/heath_check")
    public String status(){
        return "It's Working in User Service";
    }

    @GetMapping(value="/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping(value="/users")
    public String createUser(@RequestBody RequestUser requestUser){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(requestUser, UserDto.class);
        userService.createUser(userDto);
        return "Create user method is called";
    }
}
