package com.example.bookshop.security;

import com.example.bookshop.data.entities.user.UserEntity;
import com.example.bookshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookshopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public BookshopUserDetailsService(UserRepository aUserRepository) {
        userRepository = aUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String aUsername) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(aUsername);
        if (user == null) {
            throw new UsernameNotFoundException("User not found doh!");
        }
        return new BookshopUserDetails(user);
    }
}
