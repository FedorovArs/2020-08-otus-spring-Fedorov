package ru.otus.spring.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Genre;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomGenreRepositoryImpl implements CustomGenreRepository {

    private final EntityManager em;

    public CustomGenreRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Genre getByNameOrCreate(Genre genre) {
        TypedQuery<Genre> query = em.createQuery("Select g from Genre g WHERE g.name = :name", Genre.class);
        query.setParameter("name", genre.getName());
        List<Genre> genres = query.getResultList();

        if (!genres.isEmpty()) {
            return genres.get(0);
        } else {
            return em.merge(genre);
        }
    }
}
