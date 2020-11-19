package ru.otus.spring.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.entity.Book;
import ru.otus.spring.repository.BookRepository;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/api/books")
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "/api/books/{id}")
    public Mono<ResponseEntity<Book>> getBookById(@PathVariable(value = "id") String bookId) {
        return bookRepository.findById(bookId)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.notFound().build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/api/books/{id}")
    public Mono<ResponseEntity<Book>> editBookById(@PathVariable(value = "id") String bookId,
                                                   @RequestBody BookDto bookDto) {
        Book bookForUpdate = new Book(bookId, bookDto.getName(), bookDto.getAuthor(), bookDto.getGenre());
        return bookRepository.save(bookForUpdate)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @PostMapping(value = "/api/books")
    public Mono<ResponseEntity<Book>> addNewBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(new Book(bookDto.getName(), bookDto.getAuthor(), bookDto.getGenre()))
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @DeleteMapping(value = "/api/books/{id}")
    public Mono<ResponseEntity<Void>> deleteBookById(@PathVariable(value = "id") String bookId) {
        return bookRepository.deleteById(bookId)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }
}
