DROP TABLE IF EXISTS author cascade;

CREATE TABLE author
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY, -- системный идентификатор
-- photo VARCHAR(255), -- изображение с фотографией автора
-- slug VARCHAR(255) NOT NULL, -- мнемонический идентификатор автора, который будет отображаться в ссылке на его страницу
    firstname   VARCHAR(255) NOT NULL,             -- имя автора
    lastname    VARCHAR(255) NOT NULL,             -- фамилия автора
    description TEXT                               -- описание (биография, характеристика)
);