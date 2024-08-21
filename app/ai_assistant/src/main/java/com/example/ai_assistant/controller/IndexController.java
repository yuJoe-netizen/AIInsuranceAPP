package com.example.ai_assistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    
    @Autowired
    private ServerProperties serverProperties;

    @GetMapping
    public int getPort(){
        return serverProperties.getPort();
    }
}
