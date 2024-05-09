package com.example.userservice.security;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private static final String IP_ADDRESS = "127.0.0.1";

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    // spring security 5.4부터(spring-boot 3.xx이상 일듯?) WebSecurityConfigurerAdapter를 구현받는 방식 보단 직접 Bean을 만드는것을 권장
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        /*
             http.csrf().disable ... 이런 방식보다
             http.crsf( crsf -> crsf.disable) 이렇게 ramda식으로 사용하는것을 권장
             authorizeRequests가 authorizeHttpRequests로 변경
             authorizeHttpRequests에서 antMatchers가 requestMatchers로 변경된것으로 추정됨.
         */

        AuthenticationManager authenticationManager = this.AuthManagerSetting(http).build();

        // spring boot 3.0 이상 spring security 5.4 이상은 security 작성 시 람다식 권장
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authorize ->
                        authorize
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/**").access(this::hasIpAddress))
                .authenticationManager(authenticationManager)
                .headers( header -> header
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilter(getAuthenticationFilter(authenticationManager))
                .getOrBuild();


    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, userService, env);
    }


    private AuthorizationDecision hasIpAddress(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        return new AuthorizationDecision(IP_ADDRESS.matches(object.getRequest().getRemoteAddr()));
    }

    private AuthenticationManagerBuilder AuthManagerSetting(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);

        return authenticationManagerBuilder;
    }
}
