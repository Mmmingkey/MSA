package com.example.firstservice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

    @Value("${eureka.instance.instance-id}")
    private String INSTANCE_ID;

    private final Environment env;

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

    @GetMapping("/check")
    public String check(HttpServletRequest request){
        log.info("server port={}", request.getServerPort());
        return String.format("Hi, there. This is a message from First Service %s, %s"
                , env.getProperty("local.server.port")
                , INSTANCE_ID);
    }
}
