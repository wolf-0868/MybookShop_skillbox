package com.example.bookshop.services;

import com.example.bookshop.data.dto.GenreDTO;
import com.example.bookshop.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository repository;

    @Autowired
    public GenreService(GenreRepository aRepository) {
        repository = aRepository;
    }

    public List<GenreDTO> getAllGenres() {
        return repository.findAll()
                .stream()
                .map(GenreDTO::of)
                .collect(Collectors.toList());
    }

    public GenreDTO findBySlug(String aSlug) {
        return repository.findGenreEntityBySlug(aSlug)
                .map(GenreDTO::of)
                .orElse(null);
    }

    public GenreDTO findById(Long aId) {
        return repository.findById(aId)
                .map(GenreDTO::of)
                .orElse(null);
    }

}
