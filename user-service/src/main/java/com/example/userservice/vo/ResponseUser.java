package com.example.userservice.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class ResponseUser {
    private String email;
    private String name;
    private String pwd;
}
