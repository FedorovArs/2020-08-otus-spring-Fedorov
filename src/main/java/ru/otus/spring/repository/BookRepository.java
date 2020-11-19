package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
