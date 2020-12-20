drop table IF EXISTS AUTHORS;
create TABLE AUTHORS
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

-- Жанр
drop table IF EXISTS GENRES;
create TABLE GENRES
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

drop table IF EXISTS BOOKS;
create TABLE BOOKS
(
    id        BIGSERIAL  PRIMARY KEY,
    name      VARCHAR(255),
    author_id BIGINT,
    genre_id  BIGINT,

    FOREIGN KEY (author_id) REFERENCES AUTHORS (id) ON delete CASCADE,
    FOREIGN KEY (genre_id) REFERENCES GENRES (id) ON delete CASCADE
);

-- Комментарии
drop table IF EXISTS COMMENTS;
create TABLE COMMENTS
(
    id      BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    text    VARCHAR(255),

    CONSTRAINT FK_BOOK_ID FOREIGN KEY (book_id)
        REFERENCES BOOKS (id) ON delete CASCADE
);

-- Пользователи
drop table IF EXISTS USERS;
create TABLE USERS
(
    login     VARCHAR(50) not null primary key,
    position  VARCHAR(50) not null,
    password  VARCHAR(50) not null,
    authority VARCHAR(50) not null
);