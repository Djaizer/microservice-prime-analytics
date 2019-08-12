package com.analytics.prime.controller;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
public class ControllerV1 {

    @RequestMapping("/")
    public String health(){
        return "good";
    }

    @GetMapping("/report")
public String report(){
    return "1/32/24/34";
}

}
