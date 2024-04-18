package com.example.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    // spring security 5.4부터(spring-boot 3.xx이상 일듯?) WebSecurityConfigurerAdapter를 구현받는 방식 보단 직접 Bean을 만드는것을 권장
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // http.csrf().disable ... 이런 방식보다
        // http.crsf( crsf -> crsf.disable) 이렇게 ramda식으로 사용하는것을 권장
        // authorizeRequests가 authorizeHttpRequests로 변경
        // authorizeHttpRequests에서 antMatchers가 requestMatchers로 변경된것으로 추정됨.

        // spring boot 3.0 이상 spring security 5.4 이상은 security 작성 시 람다식 권장

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( request -> request
                        .requestMatchers("/users/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll())
                .headers( header -> header
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .getOrBuild();


    }
}
