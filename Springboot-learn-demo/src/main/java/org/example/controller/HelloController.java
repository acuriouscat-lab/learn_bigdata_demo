package org.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String h1(){
        System.out.println("hello h1 !!!");
        return "hello SpringBoot!";
    }

}
