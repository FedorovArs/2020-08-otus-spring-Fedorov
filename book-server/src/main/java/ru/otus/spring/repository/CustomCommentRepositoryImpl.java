package ru.otus.spring.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final EntityManager em;

    public CustomCommentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment getByTextOrCreate(Comment comment) {
        TypedQuery<Comment> query = em.createQuery("Select c from Comment c WHERE c.text = :text", Comment.class);
        query.setParameter("text", comment.getText());
        List<Comment> comments = query.getResultList();

        if (!comments.isEmpty()) {
            return comments.get(0);
        } else {
            return em.merge(comment);
        }
    }
}
