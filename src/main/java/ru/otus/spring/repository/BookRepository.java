package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}
