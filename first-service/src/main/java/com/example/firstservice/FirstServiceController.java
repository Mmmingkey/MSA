package com.example.firstservice;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

    @GetMapping("/welcome")
    public String welcome(){
        return "FirstServiceController.welcome";
    }

    @GetMapping("/message")
    public String message(@RequestHeader(value = "first-request", required = false)String header,
                          HttpServletResponse response){
        log.info(header);
        log.info(response.getHeader("first-response"));
        return "FIRST SERVER REQUEST HEADER"+header;
    }
}
