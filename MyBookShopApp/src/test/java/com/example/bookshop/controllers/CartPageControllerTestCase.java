package com.example.bookshop.controllers;

import com.example.bookshop.data.entities.enums.BookBindingType;
import com.example.bookshop.data.payloads.ChangeBookStatusPayload;
import com.example.bookshop.repositories.Book2UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class CartPageControllerTestCase {

    private final MockMvc mockMvc;
    private final Book2UserRepository book2UserRepository;

    @Autowired
    CartPageControllerTestCase(MockMvc aMockMvc, Book2UserRepository aBook2UserRepository) {
        mockMvc = aMockMvc;
        book2UserRepository = aBook2UserRepository;
    }

    @Test
    @WithUserDetails("user1@mail.com")
    void testAddOrRemoveBookToCart() throws Exception {
        boolean result = book2UserRepository.existsBookByBindingType(1L, 1L, BookBindingType.CART);

        ChangeBookStatusPayload payload = new ChangeBookStatusPayload();
        payload.setBooksIds(List.of(1L));
        payload.setStatus(BookBindingType.CART);

        mockMvc.perform(post("/changeBookStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtility.asJsonString(payload)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        assertTrue(result != book2UserRepository.existsBookByBindingType(1L, 1L, BookBindingType.CART));
    }

}
