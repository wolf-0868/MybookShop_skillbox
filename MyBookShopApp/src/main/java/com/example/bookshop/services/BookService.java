package com.example.bookshop.services;

import com.example.bookshop.data.Author;
import com.example.bookshop.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private JdbcTemplate _jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate aJdbcTemplate) {
        _jdbcTemplate = aJdbcTemplate;
    }

    public List<Book> getBooksData() {
        return _jdbcTemplate.query("SELECT * FROM book", (rs, rowNum) -> createBook(rs));
    }

    public List<Book> getPupularBooks() {
        return _jdbcTemplate.query("SELECT * FROM book WHERE is_bestseller = true", (rs, rowNum) -> createBook(rs));
    }

    public List<Book> getRecentBooks() {
        return _jdbcTemplate.queryForStream("SELECT * FROM book", (rs, rowNum) -> createBook(rs))
                .filter(book -> book.getPubDate().isAfter(LocalDate.now().minusYears(3)))
                .collect(Collectors.toList());
    }

    private Author getAuthorById(Integer aId) {
        return _jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", (rs, rowNum) -> createAuthor(rs), aId);
    }

    private Book createBook(ResultSet aResultSet) throws SQLException {
        return Book.builder()
                .id(aResultSet.getInt("id"))
                .author(getAuthorById(aResultSet.getInt("author_id")))
                .pubDate(aResultSet.getDate("pub_date").toLocalDate())
                .bestseller(aResultSet.getBoolean("is_bestseller"))
                .title(aResultSet.getString("title"))
                .price(aResultSet.getInt("price"))
                .discount(aResultSet.getInt("discount"))
                .build();
    }

    private static Author createAuthor(ResultSet aResultSet) throws SQLException {
        return Author.builder()
                .id(aResultSet.getInt("id"))
                .fullname(aResultSet.getString("name"))
                .build();
    }

}
