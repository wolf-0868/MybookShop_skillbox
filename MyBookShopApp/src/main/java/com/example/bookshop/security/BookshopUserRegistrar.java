package com.example.bookshop.security;


import com.example.bookshop.data.dto.UserDTO;
import com.example.bookshop.data.dto.drafts.DraftUserDTO;
import com.example.bookshop.data.entities.user.UserEntity;
import com.example.bookshop.data.payloads.ContactConfirmationPayload;
import com.example.bookshop.data.payloads.LoginPassConfirmationPayload;
import com.example.bookshop.exceptions.UserNotFountException;
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

    public UserDTO registerNewUser(DraftUserDTO aDraftUserDTO) {
        if (userRepository.findByEmail(aDraftUserDTO.getEmail()) == null) {
            UserEntity user = new UserEntity();
            user.setName(aDraftUserDTO.getName());
            user.setEmail(aDraftUserDTO.getEmail());
            user.setPhone(aDraftUserDTO.getPhone());
            user.setPassword(passwordEncoder.encode(aDraftUserDTO.getPassword()));
            return Optional.ofNullable(userRepository.save(user))
                    .map(UserDTO::of)
                    .orElse(null);
        }
        return null;
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

    private UserEntity getCurrentUserEntity() throws UserNotFountException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication.getPrincipal())
                .filter(BookshopUserDetails.class::isInstance)
                .map(BookshopUserDetails.class::cast)
                .map(BookshopUserDetails::getUser)
                .orElseThrow(() -> new UserNotFountException("Отсутствует текущий пользователь"));
    }

    public UserDTO getCurrentUser() throws UserNotFountException {
        return UserDTO.of(getCurrentUserEntity());
    }

    public DraftUserDTO getDraftCurrentUser() throws UserNotFountException {
        return DraftUserDTO.of(getCurrentUserEntity());
    }

    public Long getCurrentIdUser() throws UserNotFountException {
        return getCurrentUserEntity().getId();
    }

    public void updateCurrentUser(DraftUserDTO aDraftUserDTO) throws UserNotFountException {
        UserEntity user = getCurrentUserEntity();
        user.setName(aDraftUserDTO.getName());
        user.setEmail(aDraftUserDTO.getEmail());
        user.setPhone(aDraftUserDTO.getPhone());
        user.setPassword(passwordEncoder.encode(aDraftUserDTO.getPassword()));
        userRepository.save(user);
    }

}
