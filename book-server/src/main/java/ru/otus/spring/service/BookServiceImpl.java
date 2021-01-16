package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.entity.Author;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookRepository.getOne(id);
    }

    @Transactional
    public Book addNewBook(String bookName, String authorName, String genreName) {
        Author author = authorRepository.getByNameOrCreate(new Author(null, authorName));
        Genre genre = genreRepository.getByNameOrCreate(new Genre(null, genreName));

        return bookRepository.save(new Book(null, bookName, author, genre));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(long bookId, String name, String author, String genre) {
        Book book = bookRepository.getOne(bookId);

        book.setName(name);
        book.getAuthor().setName(author);
        book.getGenre().setName(genre);

        return bookRepository.save(book);
    }

    @Override
    @SneakyThrows
    @HystrixCommand(commandKey = "copyBookNames", fallbackMethod = "copyFallbackBookNames")
    public void copyBookNames(List<Book> books, List<Book> hystrixBooks) {

        // copyBookNames group
        sleepRandomly(2);

        for (int i = 0; i < books.size(); i++) {
            final String name = books.get(i).getName();
            hystrixBooks.get(i).setName(name);
        }
    }

    @Override
    @SneakyThrows
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")},
            fallbackMethod = "copyFallbackBookAuthors")
    public void copyBookAuthors(List<Book> books, List<Book> hystrixBooks) {

        //  Hystrix Javanica
        sleepRandomly(3);

        for (int i = 0; i < books.size(); i++) {
            final Author originAuthor = books.get(i).getAuthor();
            hystrixBooks.get(i).setAuthor(originAuthor);
        }
    }

    @Override
    @SneakyThrows
    @HystrixCommand(fallbackMethod = "copyFallbackBookGenres")
    public void copyBookGenres(List<Book> books, List<Book> hystrixBooks) {

        // default group
        sleepRandomly(4);

        for (int i = 0; i < books.size(); i++) {
            final Genre originGenre = books.get(i).getGenre();
            hystrixBooks.get(i).setGenre(originGenre);
        }
    }

    public void copyFallbackBookNames(List<Book> books, List<Book> hystrixBooks) {
        hystrixBooks.forEach(book -> book.setName("N/A book name"));
    }

    public void copyFallbackBookAuthors(List<Book> books, List<Book> hystrixBooks) {
        hystrixBooks.forEach(book -> book.setAuthor(new Author(null, "N/A book author")));
    }

    public void copyFallbackBookGenres(List<Book> books, List<Book> hystrixBooks) {
        hystrixBooks.forEach(book -> book.setGenre(new Genre(null, "N/A book genre")));
    }

    @SneakyThrows
    private void sleepRandomly(int sleepSeconds) {
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum == 3) {
            TimeUnit.SECONDS.sleep(sleepSeconds);
        }
    }

    @Override
    public void addedEmptyBooks(List<Book> list, int size) {
        for (long i = 0; i < size; i++) {
            Book book = new Book();
            book.setId(i + 1);
            list.add(book);
        }
    }
}
