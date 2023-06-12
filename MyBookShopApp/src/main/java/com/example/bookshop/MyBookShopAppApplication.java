package com.example.bookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MyBookShopAppApplication {

    public static void main(String[] aArgs) {
        SpringApplication.run(MyBookShopAppApplication.class, aArgs);
    }

}
