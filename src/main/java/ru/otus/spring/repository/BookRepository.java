package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.sql.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();
}
