package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.entity.Author;

import java.util.List;


public interface AuthorRepository extends JpaRepository<Author, Long> {
    default Author getByNameOrCreate(Author author) {
        List<Author> authors = this.findByName(author.getName());

        if (!authors.isEmpty()) {
            return authors.get(0);
        } else {
            return this.save(author);
        }
    }

    List<Author> findByName(String name);
}
