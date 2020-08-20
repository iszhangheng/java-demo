package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class SpringDemoRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoRedisApplication.class, args);
    }

}
