package com.example.storeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StoreProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreProjectApplication.class, args);
    }

}
