package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDaoJdbc;
import ru.otus.spring.dao.BookDaoJdbc;
import ru.otus.spring.dao.BookFullDaoJdbc;
import ru.otus.spring.dao.GenreDaoJdbc;
import ru.otus.spring.domain.*;

import java.util.List;
import java.util.Optional;

@Service
public class FullBookService {

    private final BookFullDaoJdbc bookFullDaoJdbc;
    private final BookDaoJdbc bookDaoJdbc;
    private final AuthorDaoJdbc authorDaoJdbc;
    private final GenreDaoJdbc genreDaoJdbc;

    public FullBookService(BookFullDaoJdbc bookFullDaoJdbc, BookDaoJdbc bookDaoJdbc, AuthorDaoJdbc authorDaoJdbc,
                           GenreDaoJdbc genreDaoJdbc) {

        this.bookFullDaoJdbc = bookFullDaoJdbc;
        this.bookDaoJdbc = bookDaoJdbc;
        this.authorDaoJdbc = authorDaoJdbc;
        this.genreDaoJdbc = genreDaoJdbc;
    }

    public List<BookFullView> getAllView() {
        return bookFullDaoJdbc.getAllView();
    }

    public void deleteById(int id) {
        bookFullDaoJdbc.deleteById(id);
    }

    public String getById(int id) {
        var optionalBookFullView = bookFullDaoJdbc.getViewById(id);
        return optionalBookFullView.isPresent() ? optionalBookFullView.get().toString() : "Данные по запрашиваемому id отсутствуют";
    }

    public String addNewBook(String name, String author, String genre) {
        Optional<BookFullView> bookFullView = bookFullDaoJdbc.findBookViewByNameAndAuthorAndGenre(name, author, genre);

        if (bookFullView.isPresent()) {
            return "Книга с указанными параметрами уже существует в БД, её id = " + bookFullView.get().getId();
        } else {

            Book bookEntity = bookDaoJdbc.getByNameOrCreate(name);
            Author authorEntity = authorDaoJdbc.getByNameOrCreate(author);
            Genre genreEntity = genreDaoJdbc.getByNameOrCreate(genre);

            bookFullDaoJdbc.insert(new BookFull(null, authorEntity.getId(), bookEntity.getId(), genreEntity.getId()));
            return "Книга успешно добавлена";
        }
    }

    public String updateBook(int id, String name, String author, String genre) {
        Optional<BookFullView> bookById = bookFullDaoJdbc.getViewById(id);

        if (bookById.isEmpty()) {
            return "Книги с указанным id не существует";
        } else {
            BookFullView bookFullView = bookById.get();
            if (bookFullView.getBookName().equalsIgnoreCase(name)
                    && bookFullView.getAuthorName().equalsIgnoreCase(author)
                    && bookFullView.getGenreName().equalsIgnoreCase(genre)) {
                return "Книги с указанным id уже имеет указанные атрибуты. Изменения не требуются.";
            }
        }

        Book bookEntity = bookDaoJdbc.getByNameOrCreate(name);
        Author authorEntity = authorDaoJdbc.getByNameOrCreate(author);
        Genre genreEntity = genreDaoJdbc.getByNameOrCreate(genre);

        bookFullDaoJdbc.update(new BookFull(id, authorEntity.getId(), bookEntity.getId(), genreEntity.getId()));
        return "Книга успешно обновлена";
    }
}
