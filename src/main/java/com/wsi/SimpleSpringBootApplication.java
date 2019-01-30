package com.wsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.wsi"})
public class SimpleSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSpringBootApplication.class, args);
    }

}

