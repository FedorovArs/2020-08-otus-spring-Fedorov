package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, CustomAuthorRepository {
}
