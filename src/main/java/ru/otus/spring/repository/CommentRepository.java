package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment getByTextOrCreate(Comment comment) {
        List<Comment> comments = this.findByText(comment.getText());

        if (!comments.isEmpty()) {
            return comments.get(0);
        } else {
            return this.save(comment);
        }
    }

    List<Comment> findByText(String text);
}
