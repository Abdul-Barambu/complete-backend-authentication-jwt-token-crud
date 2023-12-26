package com.abdul.springbootjwttokenauth.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class HomeController {

    @GetMapping(path = "/admin")
    public String helloAdmin(){
        return "Hello Admin";
    }
    @GetMapping(path = "/school")
    public String helloSchool(){
        return "Hello school";
    }
    @GetMapping(path = "/payer")
    public String helloPayer(){
        return "Hello payer";
    }
}
