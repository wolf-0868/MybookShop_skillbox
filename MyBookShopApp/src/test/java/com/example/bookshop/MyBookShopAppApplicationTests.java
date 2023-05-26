package com.example.bookshop;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MyBookShopAppApplicationTests {

	private final MyBookShopAppApplication application;

	@Value("${auth.secret}")
	String authSecret;

	@Autowired
	MyBookShopAppApplicationTests(MyBookShopAppApplication application) {
		this.application = application;
	}

	@Test
	void testContextLoads() {
		assertNotNull(application);
	}

	@Test
	void testVerifyAuthSecret() {
		assertThat(authSecret, Matchers.containsString("box"));
	}
}
