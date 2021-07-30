package com.thecoducer.shortenurl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToSwaggerPage() {
        return "redirect:/swagger-ui.html";
    }
}
