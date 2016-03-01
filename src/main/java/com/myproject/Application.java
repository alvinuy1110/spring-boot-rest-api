package com.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
// @EnableHypermediaSupport(type = HypermediaType.HAL)
@EnableWebMvc
@EnableSpringDataWebSupport
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}