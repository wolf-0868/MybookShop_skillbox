DROP TABLE IF EXISTS book cascade;

CREATE TABLE book
(
    id            BIGSERIAL    NOT NULL PRIMARY KEY, -- системный идентификатор
    author_id     INT          NOT NULL,             -- идентификатор автора
    pub_date      DATE         NOT NULL,             -- дата публикации
    is_bestseller BOOLEAN      NOT NULL,             -- книга очень популярна, является бестселлером
-- slug VARCHAR(255) NOT NULL, -- мнемонический идентификатор книги
    title         VARCHAR(255) NOT NULL,             -- название книги
-- image VARCHAR(255), -- изображение обложки
    description   TEXT,                              -- описание книги
    price         INT          NOT NULL,             -- цена в рублях основная
    discount      INT          NOT NULL DEFAULT 0    -- скидка в процентах или 0, если её нет
);

ALTER TABLE book
    ADD CONSTRAINT author_fk FOREIGN KEY (author_id) REFERENCES author (id);