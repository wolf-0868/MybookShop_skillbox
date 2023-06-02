package com.example.bookshop.services;

import com.example.bookshop.data.dto.GenreDTO;
import com.example.bookshop.data.entities.genre.GenreEntity;
import com.example.bookshop.exceptions.DataNotFoundException;
import com.example.bookshop.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository repository;

    public List<GenreDTO> getAllGenres() {
        return repository.findAll()
                .stream()
                .map(GenreDTO::of)
                .collect(Collectors.toList());
    }

    public GenreDTO findBySlug(String aSlug) throws DataNotFoundException {
        return repository.findBySlug(aSlug)
                .map(GenreDTO::of)
                .orElseThrow(() -> new DataNotFoundException(Map.of("slug", aSlug), GenreEntity.class.getName()));
    }

    public GenreDTO findById(Long aId) throws DataNotFoundException {
        return repository.findById(aId)
                .map(GenreDTO::of)
                .orElseThrow(() -> new DataNotFoundException(Map.of("slug", aId), GenreEntity.class.getName()));
    }

    public Set<GenreDTO> findByBookId(long aBookId) {
        return repository.findByBookId(aBookId)
                .stream()
                .map(GenreDTO::of)
                .collect(Collectors.toSet());
    }

}
