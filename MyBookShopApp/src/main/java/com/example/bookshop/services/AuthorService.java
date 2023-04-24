package com.example.bookshop.services;

import com.example.bookshop.data.dto.AuthorDTO;
import com.example.bookshop.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private AuthorRepository repository;

    @Autowired
    public AuthorService(AuthorRepository aRepository) {
        repository = aRepository;
    }

    public List<AuthorDTO> getAllAuthors() {
        return repository.findAll()
                .stream()
                .map(AuthorDTO::of)
                .collect(Collectors.toList());
    }

    public AuthorDTO findBySlug(String aSlug) {
        return repository.findAuthorEntitiesBySlug(aSlug)
                .map(AuthorDTO::of)
                .orElse(null);
    }

    public AuthorDTO findById(Long aId) {
        return repository.findById(aId)
                .map(AuthorDTO::of)
                .orElse(null);
    }

}
