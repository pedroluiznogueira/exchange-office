package com.github.pedroluiznogueira.microservices.limitsservice.controller;

import com.github.pedroluiznogueira.microservices.limitsservice.configuration.Configuration;
import com.github.pedroluiznogueira.microservices.limitsservice.bean.Limits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public Limits retrieveLimits() {
        return new Limits(configuration.getMinimum(), configuration.getMaximum());
    }
}
