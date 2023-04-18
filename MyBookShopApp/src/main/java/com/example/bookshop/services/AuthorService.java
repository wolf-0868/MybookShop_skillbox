package com.example.bookshop.services;

import com.example.bookshop.model.Author;
import com.example.bookshop.repositories.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private IAuthorRepository repository;

    @Autowired
    public AuthorService(IAuthorRepository aRepository) {
        repository = aRepository;
    }

    public List<Author> getAllAuthors() {
        return repository.findAll()
                .stream()
                .map(Author::of)
                .collect(Collectors.toList());
    }

}
