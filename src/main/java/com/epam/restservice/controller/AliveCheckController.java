package com.epam.restservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AliveCheckController {

    @GetMapping("/alive")
    public String isAlive(){
        return "I am alive!";
    }

}
