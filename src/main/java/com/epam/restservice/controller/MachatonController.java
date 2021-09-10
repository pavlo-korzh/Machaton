package com.epam.restservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MachatonController {

    @ResponseBody
    @GetMapping("/test")
    public String test(@RequestHeader(value = "test",required = false) String test) {

        System.out.println(test);

        return "test";
    }

}
