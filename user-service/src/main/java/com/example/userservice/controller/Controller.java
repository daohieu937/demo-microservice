package com.example.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"api/user"})
public class Controller {
    @GetMapping()
    public void test() {
        System.out.println("test demo user");
    }

}
