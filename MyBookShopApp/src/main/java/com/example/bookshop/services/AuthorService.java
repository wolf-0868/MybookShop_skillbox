package com.example.bookshop.services;

import com.example.bookshop.data.Author;
import com.example.bookshop.repositoryes.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private IAuthorRepository repository;

    @Autowired
    public AuthorService(IAuthorRepository aRepository) {
        repository = aRepository;
    }

    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

}
