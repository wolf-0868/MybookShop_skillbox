package com.example.bookshop.services;

import com.example.bookshop.data.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private JdbcTemplate _jdbcTemplate;

    @Autowired
    public AuthorService(JdbcTemplate aJdbcTemplate) {
        _jdbcTemplate = aJdbcTemplate;
    }

    public Author getAuthorDataById(Long aId) {
        return _jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", (rs, rowNum) -> Author.builder()
                .id(rs.getInt("id"))
                .fullname(rs.getString("name"))
                .build(), aId);
    }

    public List<Author> getAuthorsData() {
        return _jdbcTemplate.query("SELECT * FROM author", (rs, rowNum) -> Author.builder()
                .id(rs.getInt("id"))
                .fullname(rs.getString("name"))
                .build());
    }

}
