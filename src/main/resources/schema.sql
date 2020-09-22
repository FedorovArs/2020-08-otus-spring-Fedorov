DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS
(
    ID   INTEGER auto_increment PRIMARY KEY,
    NAME VARCHAR(255)
);

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS
(
    ID   INTEGER auto_increment PRIMARY KEY,
    NAME VARCHAR(255)
);

-- Жанр
DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES
(
    ID   INTEGER auto_increment PRIMARY KEY,
    NAME VARCHAR(255)
);

DROP TABLE IF EXISTS BOOKS_FULL;
CREATE TABLE BOOKS_FULL
(
    ID             INTEGER auto_increment PRIMARY KEY,
    author_name_id INTEGER,
    book_name_id   INTEGER,
    genre_name_id  INTEGER,

    FOREIGN KEY (author_name_id) REFERENCES AUTHORS (id) ON DELETE CASCADE,
    FOREIGN KEY (book_name_id) REFERENCES BOOKS (id) ON DELETE CASCADE,
    FOREIGN KEY (genre_name_id) REFERENCES GENRES (id) ON DELETE CASCADE
);

CREATE INDEX fk_authors_author_name_id ON BOOKS_FULL (author_name_id);
CREATE INDEX fk_books_book_name_id ON BOOKS_FULL (book_name_id);
CREATE INDEX fk_genres_genre_name_id ON BOOKS_FULL (genre_name_id);

CREATE VIEW BOOKS_FULL_VIEW AS
SELECT bf.id  as id,
       a.NAME as author_name,
       b.NAME as book_name,
       g.NAME as genre_name

FROM BOOKS_FULL bf
         LEFT JOIN AUTHORS a ON bf.author_name_id = a.ID
         LEFT JOIN BOOKS b on bf.book_name_id = b.ID
         LEFT JOIN GENRES g on bf.genre_name_id = g.ID


