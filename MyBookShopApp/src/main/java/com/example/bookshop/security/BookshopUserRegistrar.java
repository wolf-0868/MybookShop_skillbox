package com.example.bookshop.security;


import com.example.bookshop.data.dto.UserDTO;
import com.example.bookshop.data.entities.user.UserEntity;
import com.example.bookshop.repositories.UserRepository;
import com.example.bookshop.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookshopUserRegistrar {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookshopUserDetailsService bookshopUserDetailsService;


    @Autowired
    public BookshopUserRegistrar(JWTUtil aJwtUtil, UserRepository aUserRepository, PasswordEncoder aPasswordEncoder, AuthenticationManager aAuthenticationManager, BookshopUserDetailsService aBookshopUserDetailsService) {
        jwtUtil = aJwtUtil;
        userRepository = aUserRepository;
        passwordEncoder = aPasswordEncoder;
        authenticationManager = aAuthenticationManager;
        bookshopUserDetailsService = aBookshopUserDetailsService;
    }

    public void registerNewUser(RegistrationForm aRegistrationForm) {
        if (userRepository.findByEmail(aRegistrationForm.getEmail()) == null) {
            UserEntity user = new UserEntity();
            user.setName(aRegistrationForm.getName());
            user.setEmail(aRegistrationForm.getEmail());
            user.setPhone(aRegistrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(aRegistrationForm.getPassword()));
            userRepository.save(user);
        }
    }

    public ConfirmationResponse login(ContactConfirmationPayload aPayload) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(aPayload.getContact(), aPayload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ConfirmationResponse response = new ConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ConfirmationResponse jwtLogin(LoginPassConfirmationPayload aPayload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(aPayload.getLogin(), aPayload.getPassword()));
        BookshopUserDetails userDetails = (BookshopUserDetails) bookshopUserDetailsService.loadUserByUsername(aPayload.getLogin());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ConfirmationResponse response = new ConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    private UserEntity getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication.getPrincipal())
                .filter(BookshopUserDetails.class::isInstance)
                .map(BookshopUserDetails.class::cast)
                .map(BookshopUserDetails::getUser)
                .orElse(null);
    }

    public UserDTO getCurrentUser() {
        return Optional.ofNullable(getCurrentUserEntity())
                .map(UserDTO::of)
                .orElse(null);
    }

    public Long getCurrentIdUser() {
        return Optional.ofNullable(getCurrentUserEntity())
                .map(UserEntity::getId)
                .orElse(null);
    }

}
