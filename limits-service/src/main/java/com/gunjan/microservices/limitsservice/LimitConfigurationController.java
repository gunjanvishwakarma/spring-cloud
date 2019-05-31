package com.gunjan.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitConfigurationController
{
    @Autowired
    private Configuration configuration;
    
    @GetMapping("/limits")
    public LimitConfiguration reteriveLimitConfigurations(){
        return new LimitConfiguration(configuration.getMinimum(),configuration.getMaximum());
    }
}
