package com.example.secondservice;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
//@RequestMapping("/second-service")
public class SecondServiceController {

    @GetMapping("/welcome")
    public String welcome(){
        return "SecondServiceController.welcome";
    }

    @GetMapping("/message")
    public String message(@RequestHeader(value = "second-request", required = false)String header,
                          HttpServletResponse response){
        log.info(header);
        log.info(response.getHeader("second-response"));
        return "SECOND SERVER REQUEST HEADER"+header;
    }
}
