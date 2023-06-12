package com.example.bookshop.security;

import com.example.bookshop.data.entities.user.UserEntity;
import com.example.bookshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookshopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String aUsername) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(aUsername);
        if (user != null) {
            return new BookshopUserDetails(user);
        }
        user = userRepository.findByPhone(aUsername);
        if (user != null) {
            return new PhoneNumberUserDetails(user);
        }
        throw new UsernameNotFoundException("User not found doh!");
    }

}
