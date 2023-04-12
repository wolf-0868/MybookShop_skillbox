package com.example.bookshop.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "book")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    public static final String RUB_SIMBOL = "\u0584";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @Column(name = "pub_date", columnDefinition = "DATE")
    private LocalDate pubDate;

    @Column(name = "is_bestseller")
    private boolean bestseller;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "discount")
    private Integer discount;

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


}
