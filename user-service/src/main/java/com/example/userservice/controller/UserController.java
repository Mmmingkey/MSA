package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/user-service")
public class UserController {
    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping(value="/health_check")
    public String status(HttpServletRequest request){
        return String.format("It's Working in User Service" +
                ", port(local.server.port) = %s" +
                ", port(server.port) = %s" +
                ", token secret = %s" +
                ", token expiration time = %s"
                ,env.getProperty("local.server.port")
                ,env.getProperty("server.port")
                ,env.getProperty("token.secret")
                ,env.getProperty("token.expiration_time"));
    }

    @GetMapping(value="/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping(value="/users")
    public ResponseEntity<ResponseUser> createUser(@Valid @RequestBody RequestUser requestUser){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(requestUser, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach( vo ->
                result.add(new ModelMapper().map(vo, ResponseUser.class))
        );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable(name="id", required = true) String id){
        ResponseUser result = new ModelMapper().map(userService.getUserByUserId(id), ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
