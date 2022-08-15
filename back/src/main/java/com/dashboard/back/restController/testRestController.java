package com.dashboard.back.restController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testRestController {
    @GetMapping(value = "/RestTest")
    public String getPostList() {
        return "Rest-API-Test";
    }
}
