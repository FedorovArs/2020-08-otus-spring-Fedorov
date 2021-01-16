DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS
(
    id   BIGINT auto_increment PRIMARY KEY,
    name VARCHAR(255)
);

-- Жанр
DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES
(
    id   BIGINT auto_increment PRIMARY KEY,
    name VARCHAR(255)
);

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS
(
    id        BIGINT auto_increment PRIMARY KEY,
    name      VARCHAR(255),
    author_id BIGINT,
    genre_id  BIGINT,

    FOREIGN KEY (author_id) REFERENCES AUTHORS (id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES GENRES (id) ON DELETE CASCADE
);

-- Комментарии
DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS
(
    id      BIGINT auto_increment PRIMARY KEY,
    book_id BIGINT NOT NULL,
    text    VARCHAR(255),

    CONSTRAINT FK_BOOK_ID FOREIGN KEY (book_id)
        REFERENCES BOOKS (id) ON DELETE CASCADE
);

-- Пользователи
DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
    login     VARCHAR(50) not null primary key,
    position  VARCHAR(50) not null,
    password  VARCHAR(50) not null,
    authority VARCHAR(50) not null
);