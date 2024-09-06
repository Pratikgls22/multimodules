package com.ecommerce.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/multiModule")
@RequiredArgsConstructor
public class DemoController {


    @GetMapping("/testApi")
    public String testApi() {
        return "Test Done";
    }
}
