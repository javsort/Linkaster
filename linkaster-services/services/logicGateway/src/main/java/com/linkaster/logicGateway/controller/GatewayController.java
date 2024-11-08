package com.linkaster.logicGateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
public class GatewayController {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Logic gateway Home";
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public String status(){
        return "All good";
    }
}
