package com.example.bookshop.security;


import com.example.bookshop.data.dto.UserDTO;
import com.example.bookshop.data.dto.drafts.DraftUserDTO;
import com.example.bookshop.data.entities.user.UserEntity;
import com.example.bookshop.data.payloads.ContactConfirmationPayload;
import com.example.bookshop.data.payloads.LoginPassConfirmationPayload;
import com.example.bookshop.exceptions.BookshopUserRegistrarException;
import com.example.bookshop.exceptions.UserNotFountException;
import com.example.bookshop.repositories.UserRepository;
import com.example.bookshop.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookshopUserRegistrar {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookshopUserDetailsService bookshopUserDetailsService;

    public UserDTO registerNewUser(DraftUserDTO aDraftUserDTO) {
        UserEntity userByEmail = userRepository.findByEmail(aDraftUserDTO.getEmail());
        UserEntity userByPhone = userRepository.findByPhone(aDraftUserDTO.getPhone());

        if (userByEmail == null && userByPhone == null) {
            UserEntity user = new UserEntity();
            user.setName(aDraftUserDTO.getName());
            user.setEmail(aDraftUserDTO.getEmail());
            user.setPhone(aDraftUserDTO.getPhone());
            user.setPassword(passwordEncoder.encode(aDraftUserDTO.getPassword()));
            return UserDTO.of(userRepository.save(user));
        } else {
            return UserDTO.of(userByPhone);
        }
    }

    public ConfirmationResponse jwtLogin(LoginPassConfirmationPayload aPayload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(aPayload.getLogin(), aPayload.getPassword()));
        return new ConfirmationResponse(jwtUtil.generateToken(bookshopUserDetailsService.loadUserByUsername(aPayload.getLogin())));
    }

    public ConfirmationResponse jwtLoginByPhoneNumber(ContactConfirmationPayload aPayload) {
        DraftUserDTO draft = new DraftUserDTO();
        draft.setPhone(aPayload.getContact());
        draft.setPassword(aPayload.getCode());
        registerNewUser(draft);
        return new ConfirmationResponse(jwtUtil.generateToken(bookshopUserDetailsService.loadUserByUsername(aPayload.getContact())));
    }

    public UserEntity getCurrentUserEntity() throws UserNotFountException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication.getPrincipal())
                .filter(BookshopUserDetails.class::isInstance)
                .map(BookshopUserDetails.class::cast)
                .map(BookshopUserDetails::getUser)
                .orElseThrow(() -> new UserNotFountException("Пользователь не авторизован"));
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

    public void updateCurrentUser(DraftUserDTO aDraftUserDTO) throws UserNotFountException, BookshopUserRegistrarException {
        UserEntity user = getCurrentUserEntity();
        fillUserEntity(aDraftUserDTO, user);
        userRepository.save(user);
    }

    public void updateUser(DraftUserDTO aDraftUserDTO) throws UserNotFountException, BookshopUserRegistrarException {
        UserEntity user = userRepository.findById(aDraftUserDTO.getId())
                .orElseThrow(() -> new UserNotFountException("Не удалось найти пользователя с идентификатором '%d'", aDraftUserDTO.getId()));
        fillUserEntity(aDraftUserDTO, user);
        userRepository.save(user);
    }

    private void fillUserEntity(DraftUserDTO aDraftUserDTO, UserEntity aUserEntity) throws BookshopUserRegistrarException {
        if (!aDraftUserDTO.getEmail().equals(aUserEntity.getEmail())) {
            if (userRepository.existsByEmail(aDraftUserDTO.getEmail())) {
                throw new BookshopUserRegistrarException("Пользователь с почтой - '%s' уже зарегистрирован");
            }
            aUserEntity.setEmail(aDraftUserDTO.getEmail());
        }
        if (!aDraftUserDTO.getPhone().equals(aUserEntity.getPhone())) {
            if (userRepository.existsByPhone(aDraftUserDTO.getPhone())) {
                throw new BookshopUserRegistrarException("Пользователь с телефоном - '%s' уже зарегистрирован");
            }
            aUserEntity.setPhone(aDraftUserDTO.getPhone());
        }
        aUserEntity.setName(aDraftUserDTO.getName());
        aUserEntity.setPassword(passwordEncoder.encode(aDraftUserDTO.getPassword()));
    }

}
