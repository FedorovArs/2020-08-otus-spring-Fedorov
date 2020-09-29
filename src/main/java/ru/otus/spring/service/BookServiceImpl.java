package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDaoJdbc;
import ru.otus.spring.dao.BookDaoJdbc;
import ru.otus.spring.dao.GenreDaoJdbc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDaoJdbc bookDaoJdbc;
    private final AuthorDaoJdbc authorDaoJdbc;
    private final GenreDaoJdbc genreDaoJdbc;

    public BookServiceImpl(BookDaoJdbc bookDaoJdbc, AuthorDaoJdbc authorDaoJdbc,
                           GenreDaoJdbc genreDaoJdbc) {

        this.bookDaoJdbc = bookDaoJdbc;
        this.authorDaoJdbc = authorDaoJdbc;
        this.genreDaoJdbc = genreDaoJdbc;
    }

    public List<Book> getAll() {
        return bookDaoJdbc.getAll();
    }

    public void deleteById(long id) {
        bookDaoJdbc.deleteById(id);
    }

    public String getById(long id) {
        Optional<Book> byId = bookDaoJdbc.getById(id);
        if (byId.isPresent()) {
            return byId.get().toString();
        } else {
            return "Книга с указанным id отсутствует в БД";
        }
    }

    public String addNewBook(String bookName, String authorName, String genreName) {

        Author author = authorDaoJdbc.getByNameOrCreate(new Author(null, authorName));
        Genre genre = genreDaoJdbc.getByNameOrCreate(new Genre(null, genreName));

        bookDaoJdbc.insert(new Book(null, bookName, author, genre));
        return "Книга успешно добавлена";
    }

    public String updateBook(long id, String newBookName, String newAuthor, String newGenre) {
        Optional<Book> optionalBook = bookDaoJdbc.getById(id);

        if (optionalBook.isEmpty()) {
            return "Книга с указанным id отсутсвует в БД";
        } else {
            Book book = optionalBook.get();
            if (book.getName().equalsIgnoreCase(newBookName)
                    && book.getAuthor().getName().equalsIgnoreCase(newAuthor)
                    && book.getGenre().getName().equalsIgnoreCase(newGenre)) {
                return "Книги с указанным id уже имеет указанные атрибуты. Изменения не требуются.";
            }

            Author author = authorDaoJdbc.getByNameOrCreate(new Author(null, newAuthor));
            Genre genre = genreDaoJdbc.getByNameOrCreate(new Genre(null, newGenre));

            bookDaoJdbc.updateById(new Book(id, newBookName, author, genre));
            return "Книга успешно обновлена";
        }
    }
}
