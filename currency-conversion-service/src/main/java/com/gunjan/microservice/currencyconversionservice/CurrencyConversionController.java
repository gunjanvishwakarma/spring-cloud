package com.gunjan.microservice.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController
{
    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable String quantity)
    {
        Map<String,String> uriVariables = new HashMap();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().
                getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversionBean.class,
                        uriVariables);
        CurrencyConversionBean currencyConversionBean = responseEntity.getBody();
        return new CurrencyConversionBean(
                currencyConversionBean.getId(),
                from,
                to,
                currencyConversionBean.getConversionMultiple(),
                new BigDecimal(quantity).multiply(currencyConversionBean.getConversionMultiple()),
                new BigDecimal(quantity),
                currencyConversionBean.getPort());
    }
    
    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable String quantity)
    {

        CurrencyConversionBean currencyConversionBean = proxy.retrieveExchangeValue(from,to);
        logger.info("{}",currencyConversionBean);
        return new CurrencyConversionBean(
                currencyConversionBean.getId(),
                from,
                to,
                currencyConversionBean.getConversionMultiple(),
                new BigDecimal(quantity).multiply(currencyConversionBean.getConversionMultiple()),
                new BigDecimal(quantity),
                currencyConversionBean.getPort());
    }
}
