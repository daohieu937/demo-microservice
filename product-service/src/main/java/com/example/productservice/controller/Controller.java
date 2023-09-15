package com.example.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"api/product"})
public class Controller {
    @GetMapping()
    public void test() {
        System.out.println("test demo product");
    }

}
