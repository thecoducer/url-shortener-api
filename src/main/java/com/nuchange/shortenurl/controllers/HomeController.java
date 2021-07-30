package com.thecoducer.shortenurl.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public static String test() {
        return "hello";
    }
}
