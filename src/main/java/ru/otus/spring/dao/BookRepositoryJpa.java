package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Book;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    public long count() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Book> book = criteria.from(Book.class);
        criteria.select(builder.count(book));

        TypedQuery<Long> query = em.createQuery(criteria);
        return query.getSingleResult();
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    public List<Book> getAll() {
        EntityGraph<?> authorsEntityGraph = em.getEntityGraph("authors-entity-graph");
        TypedQuery<Book> query = em.createQuery("Select b from Book b join fetch b.genre join fetch b.comment", Book.class);
        query.setHint("javax.persistence.fetchgraph", authorsEntityGraph);
        return query.getResultList();
    }

    public void deleteById(long id) {
        Query query = em.createQuery("DELETE FROM Book b WHERE b.id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public void updateById(Book book) {
        em.merge(book);
    }
}
