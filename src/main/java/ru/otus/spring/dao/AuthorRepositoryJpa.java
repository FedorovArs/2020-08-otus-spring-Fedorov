package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    public long count() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Author> author = criteria.from(Author.class);
        criteria.select(builder.count(author));

        TypedQuery<Long> query = em.createQuery(criteria);
        return query.getSingleResult();
    }

    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    public Optional<Author> getByNameIgnoreCase(Author author) {
        TypedQuery<Author> query = em.createQuery("Select a from Author a WHERE a.name = :name", Author.class);
        query.setParameter("name", author.getName());
        List<Author> resultList = query.getResultList();
        return Optional.ofNullable(resultList.get(0));
    }

    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("Select a from Author a", Author.class);
        return query.getResultList();
    }

    public void deleteById(long id) {
        Query query = em.createQuery("DELETE FROM Author a WHERE a.id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public void updateById(Author author) {
        Query query = em.createQuery("UPDATE Author a SET a.name =:name WHERE a.id =:id");
        query.setParameter("id", author.getId());
        query.setParameter("name", author.getName());
        query.executeUpdate();
    }

    public Author getByNameOrCreate(Author author) {
        return this.getByNameIgnoreCase(author).orElseGet(() -> this.save(author));
    }
}
