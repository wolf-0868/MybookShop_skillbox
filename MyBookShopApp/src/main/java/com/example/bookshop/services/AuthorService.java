package com.example.bookshop.services;

import com.example.bookshop.data.dto.AuthorDTO;
import com.example.bookshop.data.entities.AuthorEntity;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    public List<AuthorDTO> getAllAuthors() {
        return repository.findAll()
                .stream()
                .map(AuthorDTO::of)
                .collect(Collectors.toList());
    }

    public AuthorDTO findBySlug(String aSlug) throws DataNotFoundException {
        return repository.findBySlug(aSlug)
                .map(AuthorDTO::of)
                .orElseThrow(() -> new DataNotFoundException(Map.of("slug", aSlug), AuthorEntity.class.getName()));
    }

    public AuthorDTO findById(long aId) throws DataNotFoundException {
        return repository.findById(aId)
                .map(AuthorDTO::of)
                .orElseThrow(() -> new DataNotFoundException(Map.of("id", aId), AuthorEntity.class.getName()));
    }

    public Set<AuthorDTO> findByBookId(long aBookId) {
        return repository.findByBookId(aBookId)
                .stream()
                .map(AuthorDTO::of)
                .collect(Collectors.toSet());
    }

}
