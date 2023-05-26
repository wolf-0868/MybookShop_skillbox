package com.example.bookshop.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class BookPageControllerTestCase {

    private final MockMvc mockMvc;

    @Autowired
    BookPageControllerTestCase(MockMvc aMockMvc) {
        mockMvc = aMockMvc;
    }

    @Test
    void testGetPageBySlug() throws Exception {
        mockMvc.perform(get("/books/book-sdu-49"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(xpath("/html/body/div/div/main/div/div[1]/div[2]/div[1]/h1")
                        .string("He Died With a Felafel in His Hand"));
    }

    @Test
    void testGetPageBySlugFail() throws Exception {
        mockMvc.perform(get("/books/123123123"))
                .andDo(print())
                .andExpect(xpath("/html/body/h2")
                        .string("com.example.bookshop.exceptions.DataNotFoundException"));

    }

}
