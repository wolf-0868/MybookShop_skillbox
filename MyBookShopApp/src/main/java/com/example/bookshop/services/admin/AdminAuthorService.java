package com.example.bookshop.services.admin;

import com.example.bookshop.data.dto.drafts.DraftAuthorDTO;
import com.example.bookshop.data.entities.AuthorEntity;
import com.example.bookshop.exceptions.BookshopException;
import com.example.bookshop.repositories.AuthorRepository;
import com.example.bookshop.services.ResourceStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AdminAuthorService {

    private final AuthorRepository authorRepository;
    private final ResourceStorageService storageService;

    public Page<DraftAuthorDTO> getPageAuthors(Pageable aPageable) {
        return authorRepository.findAll(aPageable)
                .map(DraftAuthorDTO::of);
    }

    public Page<DraftAuthorDTO> getPageOfSearchResultAuthors(String aSearchWord, Pageable aPageable) {
        return authorRepository.findByFullName(aSearchWord, aPageable)
                .map(DraftAuthorDTO::of);
    }

    public void saveAuthor(DraftAuthorDTO aAuthor) throws BookshopException {
        authorRepository.save(createAuthorEntity(aAuthor));
    }

    public DraftAuthorDTO findAuthorById(Long aId) throws BookshopException {
        return authorRepository.findById(aId)
                .map(DraftAuthorDTO::of)
                .orElseThrow(() -> new BookshopException("Author by id='%d' not found", aId));
    }

    public void deleteAuthorById(Long aId) throws BookshopException {
        AuthorEntity authorEntity = authorRepository.findById(aId)
                .orElseThrow(() -> new BookshopException("Author by id='%d' not found", aId));
        authorRepository.delete(authorEntity);
    }

    public void changeImageForAuthor(MultipartFile aFile, long aAuthorId) throws BookshopException, IOException {
        AuthorEntity authorEntity = authorRepository.findById(aAuthorId)
                .orElseThrow(() -> new BookshopException("Author by id='%d' not found", aAuthorId));
        String savePath = storageService.saveNewBookImage(aFile, authorEntity.getSlug());
        authorEntity.setPhoto(savePath);
        authorRepository.save(authorEntity);
    }

    private AuthorEntity createAuthorEntity(DraftAuthorDTO aPayload) throws BookshopException {
        AuthorEntity authorEntity;
        if (aPayload.getId() == null) {
            authorEntity = new AuthorEntity();
        } else {
            authorEntity = authorRepository.findById(aPayload.getId())
                    .orElseThrow(() -> new BookshopException("Author by id='%d' not found", aPayload.getId()));
        }
        authorEntity.setId(aPayload.getId());
        authorEntity.setSlug(aPayload.getSlug());
        authorEntity.setFirstname(aPayload.getFirstName());
        authorEntity.setLastname(aPayload.getLastName());
        authorEntity.setDescription(aPayload.getDescription());

        return authorEntity;
    }

}
