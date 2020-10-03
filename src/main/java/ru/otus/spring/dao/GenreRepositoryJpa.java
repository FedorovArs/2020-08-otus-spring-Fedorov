package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Genre;

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
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    public long count() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Genre> genre = criteria.from(Genre.class);
        criteria.select(builder.count(genre));

        TypedQuery<Long> query = em.createQuery(criteria);
        return query.getSingleResult();
    }

    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    public Optional<Genre> getByNameIgnoreCase(Genre genre) {
        TypedQuery<Genre> query = em.createQuery("Select g from Genre g WHERE g.name = :name", Genre.class);
        query.setParameter("name", genre.getName());
        List<Genre> resultList = query.getResultList();
        return Optional.ofNullable(resultList.get(0));
    }

    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("Select g from Genre g", Genre.class);
        return query.getResultList();
    }

    public void deleteById(long id) {
        Query query = em.createQuery("DELETE FROM Genre g WHERE g.id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public void updateById(Genre genre) {
        Query query = em.createQuery("UPDATE Genre g SET g.name =:name WHERE g.id =:id");
        query.setParameter("id", genre.getId());
        query.setParameter("name", genre.getName());
        query.executeUpdate();
    }

    public Genre getByNameOrCreate(Genre genre) {
        return this.getByNameIgnoreCase(genre).orElseGet(() -> this.save(genre));
    }
}
