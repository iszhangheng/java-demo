package com.example.securitydemo.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomFilter extends UsernamePasswordAuthenticationFilter {

    protected void configure(AuthenticationManagerBuilder auth) {

    }

}
