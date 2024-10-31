package com.scentbird.scentbirdservicediscovery.engine.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
