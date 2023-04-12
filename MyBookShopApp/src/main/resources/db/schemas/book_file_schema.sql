DROP TABLE IF EXISTS book_file;

CREATE TABLE book_file
(
    id      BIGSERIAL    NOT NULL PRIMARY KEY, -- системный идентификатор
    hash    VARCHAR(255) NOT NULL,             -- случайный хэш, предназначенный для идентификации файла при скачивании.
    type_id INT          NOT NULL,             -- тип файла
    path    VARCHAR(255) NOT NULL              -- путь к файлу
);