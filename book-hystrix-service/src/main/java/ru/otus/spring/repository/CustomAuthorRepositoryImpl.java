package ru.otus.spring.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomAuthorRepositoryImpl implements CustomAuthorRepository {

    private final EntityManager em;

    public CustomAuthorRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author getByNameOrCreate(Author author) {
        TypedQuery<Author> query = em.createQuery("Select a from Author a WHERE a.name = :name", Author.class);
        query.setParameter("name", author.getName());
        List<Author> authors = query.getResultList();

        if (!authors.isEmpty()) {
            return authors.get(0);
        } else {
            return em.merge(author);
        }
    }
}
