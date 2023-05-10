package com.example.bookshop.services;

import com.example.bookshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository aUserRepository) {
        userRepository = aUserRepository;
    }



}
