package com.gunjan.microservices.limitsservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @GetMapping("/fault-tolerance-example")
    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
    public LimitConfiguration reteriveConfigurations(){
        throw new RuntimeException("Not available");
    }

    public LimitConfiguration fallbackRetrieveConfiguration(){
        return new LimitConfiguration(10,100);
    }
}
