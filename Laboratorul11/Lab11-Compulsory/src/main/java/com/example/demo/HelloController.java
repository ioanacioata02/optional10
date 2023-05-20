package com.example.demo;


import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String sayHello() {
        return "Greetings from Spring Boot!";
    }
}