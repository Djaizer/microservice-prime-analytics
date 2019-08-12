package com.analytics.prime;


import com.analytics.prime.controller.ControllerV1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(ControllerV1.class, args);
    }
}
