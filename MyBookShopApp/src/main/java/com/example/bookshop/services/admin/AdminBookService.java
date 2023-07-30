package com.example.bookshop.services.admin;

import com.example.bookshop.data.dto.drafts.DraftAuthorBookDTO;
import com.example.bookshop.data.dto.drafts.DraftBookDTO;
import com.example.bookshop.data.entities.AuthorEntity;
import com.example.bookshop.data.entities.BookEntity;
import com.example.bookshop.data.entities.book.links.Book2AuthorEntity;
import com.example.bookshop.exceptions.BookshopException;
import com.example.bookshop.repositories.AuthorRepository;
import com.example.bookshop.repositories.BookRepository;
import com.example.bookshop.repositories.BookReviewRepository;
import com.example.bookshop.services.ResourceStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminBookService {

    static final String BOOK_NOT_FOUNT_FORMAT = "Book by id='%d' not found";

    private final BookRepository bookRepository;
    private final BookReviewRepository bookReviewRepository;
    private final AuthorRepository authorRepository;
    private final ResourceStorageService storageService;

    public Page<DraftBookDTO> getPageBooks(Pageable aPageable) {
        return bookRepository.findAll(aPageable)
                .map(DraftBookDTO::of);
    }

    public Page<DraftBookDTO> getPageOfSearchResultBooks(String aSearchWord, Pageable aPageable) {
        return bookRepository.findByTitleContainingIgnoreCase(aSearchWord, aPageable)
                .map(DraftBookDTO::of);
    }

    public List<DraftAuthorBookDTO> getAuthorsBookPayload() {
        return authorRepository.findAll()
                .stream()
                .map(DraftAuthorBookDTO::of)
                .collect(Collectors.toList());
    }

    public void saveBook(DraftBookDTO aBook) throws BookshopException {
        bookRepository.save(createBookEntity(aBook));
    }

    public DraftBookDTO findBookById(Long aId) throws BookshopException {
        return bookRepository.findById(aId)
                .map(DraftBookDTO::of)
                .orElseThrow(() -> new BookshopException(BOOK_NOT_FOUNT_FORMAT, aId));
    }

    public void deleteBookById(Long aId) throws BookshopException {
        BookEntity bookEntity = bookRepository.findById(aId)
                .orElseThrow(() -> new BookshopException(BOOK_NOT_FOUNT_FORMAT, aId));
        bookReviewRepository.deleteAllByBook(bookEntity);
        bookRepository.delete(bookEntity);
    }

    public void changeImageForBook(MultipartFile aFile, long aBookId) throws BookshopException, IOException {
        BookEntity bookEntity = bookRepository.findById(aBookId)
                .orElseThrow(() -> new BookshopException(BOOK_NOT_FOUNT_FORMAT, aBookId));
        String savePath = storageService.saveNewBookImage(aFile, bookEntity.getSlug());
        bookEntity.setImage(savePath);
        bookRepository.save(bookEntity);
    }

    private BookEntity createBookEntity(DraftBookDTO aPayload) throws BookshopException {
        BookEntity bookEntity = null;
        if (aPayload.getId() == null) {
            bookEntity = new BookEntity();
        } else {
            bookEntity = bookRepository.findById(aPayload.getId())
                    .orElseThrow(() -> new BookshopException(BOOK_NOT_FOUNT_FORMAT, aPayload.getId()));
        }
        bookEntity.setId(aPayload.getId());
        bookEntity.setSlug(aPayload.getSlug());
        bookEntity.setTitle(aPayload.getTitle());
        bookEntity.setPubDate(aPayload.getPubDate());
        bookEntity.setBestseller(aPayload.isBestseller());
        bookEntity.setPrice(aPayload.getPrice());
        bookEntity.setDiscount(aPayload.getDiscount());
        bookEntity.setDescription(aPayload.getDescription());

        if (aPayload.getAuthorId() != null) {
            AuthorEntity authorEntity = authorRepository.findById(aPayload.getAuthorId())
                    .orElseThrow(() -> new BookshopException(AdminAuthorService.AUTHOR_NOT_FOUNT_FORMAT, aPayload.getAuthorId()));
            Book2AuthorEntity book2AuthorEntity = new Book2AuthorEntity();
            book2AuthorEntity.setBook(bookEntity);
            book2AuthorEntity.setAuthor(authorEntity);
            bookEntity.setAuthors(Set.of(book2AuthorEntity));
        }

        return bookEntity;
    }
}
