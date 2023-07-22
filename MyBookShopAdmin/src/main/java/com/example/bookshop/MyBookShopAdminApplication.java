package com.example.bookshop;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class MyBookShopAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBookShopAdminApplication.class, args);
	}

}
