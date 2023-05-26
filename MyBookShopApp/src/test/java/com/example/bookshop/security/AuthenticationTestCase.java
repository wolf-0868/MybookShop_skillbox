package com.example.bookshop.security;

import com.example.bookshop.data.dto.UserDTO;
import com.example.bookshop.data.dto.drafts.DraftUserDTO;
import com.example.bookshop.data.entities.user.UserEntity;
import com.example.bookshop.repositories.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class AuthenticationTestCase {

    private final BookshopUserRegistrar userRegister;
    private final PasswordEncoder passwordEncoder;
    private DraftUserDTO testUser;

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    AuthenticationTestCase(BookshopUserRegistrar aUserRegister, PasswordEncoder aPasswordEncoder) {
        userRegister = aUserRegister;
        passwordEncoder = aPasswordEncoder;
    }

    @BeforeEach
    void setUp() {
        testUser = DraftUserDTO.builder()
                .name("Tester")
                .email("test@mail.org")
                .phone("9031232323")
                .password("123456")
                .passwordReply("123456")
                .build();
    }

    @AfterEach
    void tearDown() {
        testUser = null;
    }

    @Test
    void testPasswordEncoder() {
        String encode = passwordEncoder.encode(testUser.getPassword());
        assertTrue(passwordEncoder.matches(testUser.getPassword(), encode));
    }

    @Test
    void testRegisterNewUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(testUser.getName());
        userEntity.setEmail(testUser.getEmail());
        userEntity.setPhone(testUser.getPhone());
        userEntity.setPassword(passwordEncoder.encode(testUser.getPassword()));

        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO user = userRegister.registerNewUser(testUser);

        assertNotNull(user);
        assertTrue(CoreMatchers.is(user.getName())
                .matches(testUser.getName()));
        assertTrue(CoreMatchers.is(user.getEmail())
                .matches(testUser.getEmail()));

        Mockito.verify(userRepositoryMock, Mockito.times(1))
                .save(Mockito.any(UserEntity.class));
    }

    @Test
    void testRegisterNewUserFail() {
        Mockito.doReturn(new UserEntity())
                .when(userRepositoryMock)
                .findByEmail(testUser.getEmail());
        assertNull(userRegister.registerNewUser(testUser));
    }

}
