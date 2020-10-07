package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.entity.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    default Genre getByNameOrCreate(Genre genre) {
        List<Genre> genres = this.findByName(genre.getName());

        if (!genres.isEmpty()) {
            return genres.get(0);
        } else {
            return this.save(genre);
        }
    }

    List<Genre> findByName(String name);
}
