package com.finalproject.bugme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class BugmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BugmeApplication.class, args);
    }

}
