package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>, CustomGenreRepository {
}
