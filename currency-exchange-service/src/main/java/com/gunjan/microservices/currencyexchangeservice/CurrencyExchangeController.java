package com.gunjan.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController
{
    @Autowired
    private Environment environment;
    
    @Autowired
    ExchangeValueRepository repository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue reteriveExchangeValue(@PathVariable String from, @PathVariable String to){
        ExchangeValue exchangeValue = repository.findByFromAndTo(from,to);
        logger.info("{}",exchangeValue);
        exchangeValue.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
        return exchangeValue;
    }
}
