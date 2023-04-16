package com.example.bookshop.data;

import com.example.bookshop.data.struct.book.links.Book2AuthorEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table(name = "book")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookEntity {

    public static final String RUB_SIMBOL = "\u0584";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter(AccessLevel.MODULE)
    @Setter(AccessLevel.MODULE)
    @OneToMany(mappedBy = "author")
    private Set<Book2AuthorEntity> bookAuthors = new HashSet<>();

    @Column(name = "pub_date", columnDefinition = "DATE", nullable = false)
    private LocalDate pubDate;

    @Column(name = "is_bestseller", nullable = false)
    private boolean bestseller;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "discount", nullable = false)
    private Integer discount = 0;

    public Integer getCurrentPrice() {
        return price - (int) (((double) price) / 100 * discount);
    }

    public String getPriceText() {
        return RUB_SIMBOL + price;
    }

    public String getCurrentPriceText() {
        return RUB_SIMBOL + getCurrentPrice();
    }

    public boolean isBigDiscount() {
        return discount >= 30;
    }

    // TODO Временный костыл для корректного отображения web страниц
    public AuthorEntity getAuthor() {
        return bookAuthors.stream()
                .map(Book2AuthorEntity::getAuthor)
                .findFirst()
                .orElse(null);
    }

}
