package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Comment;

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
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private EntityManager em;


    public long count() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Comment> comment = criteria.from(Comment.class);
        criteria.select(builder.count(comment));

        TypedQuery<Long> query = em.createQuery(criteria);
        return query.getSingleResult();
    }

    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    public Optional<Comment> getByTextIgnoreCase(Comment comment) {
        TypedQuery<Comment> query = em.createQuery("Select c from Comment c WHERE c.text = :text", Comment.class);
        query.setParameter("text", comment.getText());
        List<Comment> resultList = query.getResultList();
        return Optional.ofNullable(resultList.get(0));
    }

    public List<Comment> getAll() {
        TypedQuery<Comment> query = em.createQuery("Select c from Comment c", Comment.class);
        return query.getResultList();
    }

    public void deleteById(long id) {
        Query query = em.createQuery("DELETE FROM Comment c WHERE c.id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public void updateById(Comment comment) {
        Query query = em.createQuery("UPDATE Comment c SET c.text =:text WHERE c.id =:id");
        query.setParameter("id", comment.getId());
        query.setParameter("text", comment.getText());
        query.executeUpdate();
    }

    public Comment getByTextOrCreate(Comment comment) {
        return this.getByTextIgnoreCase(comment).orElseGet(() -> this.save(comment));
    }
}
