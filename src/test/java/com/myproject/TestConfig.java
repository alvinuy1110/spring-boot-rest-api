package com.myproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

// To support testing only
@Configuration
public class TestConfig {
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

}
