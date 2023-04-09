DROP TABLE IF EXISTS author;

CREATE TABLE author
(
    id          INT          NOT NULL AUTO_INCREMENT, -- системный идентификатор
--     photo       VARCHAR(255),                         -- изображение с фотографией автора
--     slug        VARCHAR(255) NOT NULL,                -- мнемонический идентификатор автора, который будет отображаться в ссылке на его страницу
    name        VARCHAR(255) NOT NULL,                -- имя и фамилия автора
    description TEXT                                  -- описание (биография, характеристика)
);
