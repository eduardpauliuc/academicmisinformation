package com.example.iss_project;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping
    public String helloWorld(){
        return "Hello World from Spring Boot";
    }

    @RequestMapping("/goodbye")
    public String goodbye(){
        return "Goodbye from Spring Boot";
    }

    @RequestMapping("/ciao")
    public String ciao(){
        return "Ciao from Spring Boot";
    }
}
